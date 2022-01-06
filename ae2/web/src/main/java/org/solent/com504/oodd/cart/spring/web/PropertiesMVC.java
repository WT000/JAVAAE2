/*
 * Copyright 2022 WT000
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.solent.com504.oodd.cart.spring.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.bank.model.dto.CreditCard;
import org.solent.com504.oodd.cart.web.PropertiesDao;
import org.solent.com504.oodd.cart.web.WebObjectFactory;
import org.solent.com504.oodd.bank.client.impl.BankRestClientImpl;
import org.solent.com504.oodd.bank.card.checker.RegexCardValidator;
import org.solent.com504.oodd.bank.card.checker.CardValidationResult;
import org.solent.com504.oodd.bank.model.dto.TransactionReplyMessage;
import org.solent.com504.oodd.bank.model.dto.BankTransactionStatus;
import org.solent.com504.oodd.password.PasswordUtils;

/**
 * The MVC controller for setting properties
 * @author WT000
 */
@Controller
@RequestMapping("/")
public class PropertiesMVC {

    final static Logger LOG = LogManager.getLogger(PropertiesMVC.class);
    private final PropertiesDao adminSettings = WebObjectFactory.getPropertiesDao();

    private User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            sessionUser = new User();
            sessionUser.setUsername("anonymous");
            sessionUser.setUserRole(UserRole.ANONYMOUS);
            session.setAttribute("sessionUser", sessionUser);
        }
        return sessionUser;
    }

    /**
     *
     * @param model Attributes
     * @param session Session
     * @return If successful, the properties page
     */
    @RequestMapping(value = "/properties", method = {RequestMethod.GET})
    public String getProperties(Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        
        if (sessionUser.getUserRole() != UserRole.ADMINISTRATOR) {
            LOG.debug("non-admin attempted to enter properties");
            return "redirect:/home";
        }

        String message = "";
        String errorMessage = "";
        
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("currentURL", adminSettings.getProperty("org.solent.com504.oodd.ae2.url"));
        model.addAttribute("currentCardNo", adminSettings.getProperty("org.solent.com504.oodd.ae2.cardNumber"));
        model.addAttribute("currentUsername", adminSettings.getProperty("org.solent.com504.oodd.ae2.username"));
        model.addAttribute("currentCardName", adminSettings.getProperty("org.solent.com504.oodd.ae2.cardName"));
        model.addAttribute("currentCardDate", adminSettings.getProperty("org.solent.com504.oodd.ae2.cardDate"));

        return "properties";
    }

    /**
     * 
     * @param bankURL The bank URL to set to (usually ends in /rest)
     * @param bankCard The bank card number
     * @param bankUsername The bank account username
     * @param bankPassword The bank account password
     * @param bankCardName (optional) Name on card
     * @param bankCardDate (optional) Expiry date on card
     * @param model Attributes
     * @param session Session
     * @return If successful, the properties page with updated values
     */
    @RequestMapping(value = "/properties", method = {RequestMethod.POST})
    public String setProperties(
            @RequestParam(value = "BankURL", required = false) String bankURL,
            @RequestParam(value = "BankCard", required = false) String bankCard,
            @RequestParam(value = "BankUsername", required = false) String bankUsername,
            @RequestParam(value = "BankPassword", required = false) String bankPassword,
            @RequestParam(value = "BankCardName", required = false) String bankCardName,
            @RequestParam(value = "BankCardExpiry", required = false) String bankCardDate,
            Model model,
            HttpSession session) {

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        
        if (sessionUser.getUserRole() != UserRole.ADMINISTRATOR) {
            return "redirect:/home";
        }

        String message = "";
        String errorMessage = "";

        // Ensure the needed details are valid, then test them
        if (!bankURL.equals("") && !bankCard.equals("") && !bankUsername.equals("") && !bankPassword.equals("")) {
            if (bankCard.length() != 16) {
                errorMessage += "The length of the bank card isn't 16.<br>";
            } else {
                CardValidationResult cardResult = RegexCardValidator.isValid(bankCard);
                if (!cardResult.isValid()) {
                    errorMessage += "The card isn't valid.<br>";
                }
            }

            if (!bankCardDate.equals("")) {
                CreditCard temp = new CreditCard();
                temp.setEndDate(bankCardDate);
                if (temp.cardDateExpiredOrError()) {
                    errorMessage += "The card has expired.<br>";
                }
            }

        } else {
            errorMessage += "Required field(s) are empty.<br>";
        }

        if (errorMessage.length() == 0) {
             // No errors were found, we can attempt to set the new properties.
             BankRestClientImpl testClient = new BankRestClientImpl(bankURL);
             CreditCard testCard = new CreditCard(bankCard, bankCardName, bankCardDate, "111");

            try {
                TransactionReplyMessage testResponse = testClient.transferMoney(testCard, testCard, 0.0, bankUsername, bankPassword);
                
                if (testResponse.getStatus() == BankTransactionStatus.SUCCESS) {
                    adminSettings.setProperty("org.solent.com504.oodd.ae2.url", bankURL);
                    adminSettings.setProperty("org.solent.com504.oodd.ae2.cardNumber", bankCard);
                    adminSettings.setProperty("org.solent.com504.oodd.ae2.username", bankUsername);
                    adminSettings.setProperty("org.solent.com504.oodd.ae2.password", bankPassword);
                    adminSettings.setProperty("org.solent.com504.oodd.ae2.hashedPassword", PasswordUtils.hashPassword(bankPassword));
                    
                    // Optional information
                    adminSettings.setProperty("org.solent.com504.oodd.ae2.cardName", bankCardName);
                    adminSettings.setProperty("org.solent.com504.oodd.ae2.cardDate", bankCardDate);
                    
                } else {
                    errorMessage += "The entered details don't exist on the remote bank.<br>";
                }
            } catch (Exception e) {
                errorMessage += "The entered bank URL is invalid.<br>";
                errorMessage += e.getMessage() + "<br>";
            }
        }
        
        // Check to ensure an error or Exception didn't happen
        if (errorMessage.length() > 0){
            errorMessage += "Changed reverted.";
        } else {
            message = "The new properties have been set.";
        }

        model.addAttribute("currentURL", adminSettings.getProperty("org.solent.com504.oodd.ae2.url"));
        model.addAttribute("currentCardNo", adminSettings.getProperty("org.solent.com504.oodd.ae2.cardNumber"));
        model.addAttribute("currentUsername", adminSettings.getProperty("org.solent.com504.oodd.ae2.username"));
        model.addAttribute("currentCardName", adminSettings.getProperty("org.solent.com504.oodd.ae2.cardName"));
        model.addAttribute("currentCardDate", adminSettings.getProperty("org.solent.com504.oodd.ae2.cardDate"));
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "properties";
    }
    
    /*
     * Default exception handler, catches all exceptions, redirects to friendly
     * error page. Does not catch request mapping errors
     */

    /**
     *
     * @param e The Exception
     * @param model Attributes
     * @param session Session
     * @param request Request
     * @return Error page
     */
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model,
            HttpSession session, HttpServletRequest request
    ) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        final String strStackTrace = sw.toString(); // stack trace as a string
        String urlStr = "not defined";
        if (request != null) {
            StringBuffer url = request.getRequestURL();
            urlStr = url.toString();
        }
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("requestUrl", urlStr);
        model.addAttribute("strStackTrace", strStackTrace);
        model.addAttribute("exception", e);
        //logger.error(strStackTrace); // send to logger first
        return "error"; // default friendly exception message for sessionUser
    }
}
