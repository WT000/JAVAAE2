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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.transaction.Transactional;

import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.InvoiceStatus;
import org.solent.com504.oodd.bank.client.impl.BankRestClientImpl;
import org.solent.com504.oodd.bank.model.dto.BankTransactionStatus;
import org.solent.com504.oodd.bank.model.dto.CreditCard;
import org.solent.com504.oodd.bank.model.dto.TransactionReplyMessage;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.web.PropertiesDao;
import org.solent.com504.oodd.cart.web.WebObjectFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Will
 */
@Controller
@RequestMapping("/")
public class OrdersMVC {

    final static Logger LOG = LogManager.getLogger(OrdersMVC.class);
    private final PropertiesDao adminSettings = WebObjectFactory.getPropertiesDao();

    @Autowired
    UserRepository userRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

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
    
    @RequestMapping(value = "/orders", method = {RequestMethod.GET})
    public String getAllOrders(
            @RequestParam(value = "toFind", required = false) String toFind,
            Model model,
            HttpSession session) {
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        
        String message = "";
        String errorMessage = "";
        
        if (sessionUser.getUserRole() == UserRole.ANONYMOUS) {
            errorMessage = "You must be logged in to see orders.";
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        }

        List<Invoice> ordersToShow = new ArrayList<Invoice>();

        // Search if that's the action, but ensure it's an admin doing it
        if (null != toFind && sessionUser.getUserRole() == UserRole.ADMINISTRATOR) {
            model.addAttribute("didSearch", true);
            ordersToShow = invoiceRepository.findByPurchaserUsername(toFind);
            
        } else {
            if (sessionUser.getUserRole() != UserRole.ADMINISTRATOR) {
                ordersToShow = invoiceRepository.findByPurchaserUsername(sessionUser.getUsername());
            } else {
                ordersToShow = invoiceRepository.findAllInvoices();
            }
        }

        if (ordersToShow.isEmpty()) {
            errorMessage = "We couldn't find any orders :(";
        }

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("orders", ordersToShow);
        model.addAttribute("selectedPage", "orders");

        // If searching for a specific item, users can also see hidden items
        return "orders";
    }

    @RequestMapping(value = "/viewModifyOrder", method = {RequestMethod.GET})
    public String getOrder(
            @RequestParam(value = "invoiceNumber", required = true) String invoiceNumber,
            Model model,
            HttpSession session) {

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        String errorMessage = "";

        List<Invoice> specificInvoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);

        if (specificInvoice.isEmpty()) {
            errorMessage = "Couldn't find the invoice :(";
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        }

        Invoice invoiceToDisplay = specificInvoice.get(0);

        if (!Objects.equals(invoiceToDisplay.getPurchaser().getId(), sessionUser.getId())) {
            // If they're not the user, make sure they're an admin, else don't render
            if (sessionUser.getUserRole() != UserRole.ADMINISTRATOR) {
                errorMessage = "Your account isn't permitted to view the invoice, are you signed in?";
                model.addAttribute("errorMessage", errorMessage);
                model.addAttribute("selectedPage", "home");
                return "home";
            }
        }

        model.addAttribute("savedPurchasedItems", invoiceToDisplay.getSavedBasketItems());
        model.addAttribute("invoice", invoiceToDisplay);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("selectedPage", "orders");

        return "viewModifyOrder";
    }
    
    @RequestMapping(value = "/viewModifyOrder", method = {RequestMethod.POST})
    @Transactional
    public synchronized String alterOrder(
            @RequestParam(value = "invoiceNumber", required = true) String invoiceNumber,
            @RequestParam(value = "action", required = true) String action,
            @RequestParam(value = "status", required = false) String status,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAtt) {

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        String message = "";
        String errorMessage = "";

        List<Invoice> specificInvoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);

        if (specificInvoice.isEmpty()) {
            errorMessage = "Couldn't find the invoice :(";
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        }

        if (sessionUser.getUserRole() != UserRole.ADMINISTRATOR) {
            errorMessage = "Your account isn't permitted to do this, are you signed in?";
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        }

        Invoice invoiceToDisplay = specificInvoice.get(0);

        boolean alreadyRefunded = false;
        if (invoiceToDisplay.getCurrentStatus() == InvoiceStatus.REFUNDED) {
            errorMessage = "The invoice is already refunded.";
            alreadyRefunded = true;
        }

        if (!alreadyRefunded) {
            if ("updateStatus".equals(action)) {
                invoiceToDisplay.setCurrentStatus(InvoiceStatus.valueOf(status));
                invoiceRepository.save(invoiceToDisplay);
                message = "Status has been updated.";

            } else if ("refund".equals(action)) {
                // Follow a process similar to transactions, but the bank card is used as the toCard
                BankRestClientImpl restClient = new BankRestClientImpl(adminSettings.getProperty("org.solent.com504.oodd.ae2.url"));
                CreditCard bankCard = new CreditCard(adminSettings.getProperty("org.solent.com504.oodd.ae2.cardNumber"));
                bankCard.setName(adminSettings.getProperty("org.solent.com504.oodd.ae2.cardName"));
                bankCard.setEndDate(adminSettings.getProperty("org.solent.com504.oodd.ae2.cardDate"));
                CreditCard customerCard = new CreditCard(invoiceToDisplay.getPaymentCardNumber());

                try {
                    TransactionReplyMessage result = restClient.transferMoney(bankCard, customerCard, invoiceToDisplay.getAmountDue());

                    if (result.getStatus() == BankTransactionStatus.SUCCESS) {
                        invoiceToDisplay.setCurrentStatus(InvoiceStatus.REFUNDED);
                        invoiceRepository.save(invoiceToDisplay);
                        redirectAtt.addAttribute("message", "Refund complete.");
                        return "redirect:/viewModifyOrder?invoiceNumber=" + invoiceToDisplay.getInvoiceNumber();
                    } else {
                        errorMessage = "Something went wrong: " + result.getMessage();
                    }
                } catch (Exception e) {
                    errorMessage = "Couldn't connect to the bank, check the configured properties.";
                }
            }
        }
        
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("savedPurchasedItems", invoiceToDisplay.getSavedBasketItems());
        model.addAttribute("invoice", invoiceToDisplay);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("selectedPage", "orders");

        return "viewModifyOrder";
    }

    /*
     * Default exception handler, catches all exceptions, redirects to friendly
     * error page. Does not catch request mapping errors
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
