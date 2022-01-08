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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.Address;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.bank.model.dto.CreditCard;
import org.solent.com504.oodd.bank.card.checker.RegexCardValidator;
import org.solent.com504.oodd.bank.card.checker.CardValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The MVC controller for User's and logging in
 * @author WT000
 */
@Controller
@RequestMapping("/")
public class UserMVC {

    final static Logger LOG = LogManager.getLogger(UserMVC.class);

    @Autowired
    UserRepository userRepository;

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
     * @return If successful, the home page
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(Model model,
            HttpSession session) {
        String message = "You have been successfully logged out.";
        String errorMessage = "";
        
        // Logout of session and clear
        session.invalidate();

        return "redirect:/home";
    }

    /**
     *
     * @param model Attributes
     * @param session Session
     * @return The login page
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(Model model, HttpSession session) {
        String message = "Please log in using your username and password.";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        
        // Only allow login if they're not already logged in
        if (!UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "User " + sessionUser.getUsername() + "is already logged in.";
            LOG.warn(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("selectedPage", "login");

        return "login";
    }

    /**
     *
     * @param action The action
     * @param username The username to login with
     * @param password The password to login with
     * @param model Attributes
     * @param session Session
     * @return If successful, the home page after logging in
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @Transactional
    public String login(@RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        LOG.debug("login for username=" + username);

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        model.addAttribute("selectedPage", "login");
        
        // Only allow login if not already logged in
        if (!UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "User " + sessionUser.getUsername() + "is already logged in.";
            LOG.warn(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        };
        
        // No username was entered
        if (username == null || username.trim().isEmpty()) {
            errorMessage = "You must enter a username.";
            model.addAttribute("errorMessage", errorMessage);
            return "login";
        }

        List<User> userList = userRepository.findByUsername(username);

        if ("login".equals(action)) {
            LOG.debug("logging in user username=" + username);
            
            // Check for errors and only login if information is valid
            if (userList.isEmpty()) {
                errorMessage = "Couldn't find an account with the username: " + username + ".";
                LOG.warn(errorMessage);
                model.addAttribute("errorMessage", errorMessage);
                return "login";
            }
            
            if (password == null) {
                errorMessage = "You must enter a password.";
                LOG.warn(errorMessage);
                model.addAttribute("errorMessage", errorMessage);
                return "login";
            }

            User loginUser = userList.get(0);
            if (!loginUser.isValidPassword(password)) {
                model.addAttribute("errorMessage", "Invalid username or password.");
                return "login";
            }

            if (!loginUser.getEnabled()) {
                model.addAttribute("errorMessage", username + "'s account has been disabled.");
                return "login";
            }

            // Set the sessionUser to be the logged in user
            session.setAttribute("sessionUser", loginUser);
            return "redirect:/home";
        } else {
            LOG.error("login page unknown action requested:" + action);
            
            model.addAttribute("errorMessage", "Unknown action requested:" + action + ".");
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        }
    }

    /**
     *
     * @param model Attributes
     * @param session Session
     * @return The register page
     */
    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String registerGet(Model model, HttpSession session) {
        String message = "Please enter the fields below to register on the site.";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("selectedPage", "register");

        return "register";
    }

    /**
     *
     * @param action The action
     * @param username The username to set
     * @param password The password to set
     * @param password2 The confirmed password
     * @param model Attributes
     * @param session Session
     * @return If successful, the home page after creating the account
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @Transactional
    public String registerPost(@RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "password2", required = false) String password2,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        LOG.debug("register new username=" + username);

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("selectedPage", "register");

        if (username == null || username.trim().isEmpty()) {
            errorMessage = "You must enter a username.";
            model.addAttribute("errorMessage", errorMessage);
            return "register";
        }

        // Find the username, then create the account if the info is valid
        List<User> userList = userRepository.findByUsername(username);

        if ("createNewAccount".equals(action)) {
            if (!userList.isEmpty()) {
                errorMessage = username + " already exists, please enter another username.";
                LOG.warn(errorMessage);
                model.addAttribute("errorMessage", errorMessage);
                return "register";
            }
            if (password == null || !password.equals(password2) || password.length() < 8) {
                errorMessage = "Failed the password check, please ensure it's more"
                        + " than 8 characters and you've entered the same password"
                        + " twice.";
                LOG.warn(errorMessage);
                model.addAttribute("errorMessage", errorMessage);
                return "register";
            }

            User modifyUser = new User();
            modifyUser.setUserRole(UserRole.CUSTOMER);
            modifyUser.setUsername(username);
            modifyUser.setPassword(password);
            modifyUser = userRepository.save(modifyUser);

            // If already logged in - keep session modifyUser else set session modifyUser to modifyUser
            // else set session modifyUser the newly created modifyUser (i.e. automatically log in)
            if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
                session.setAttribute("sessionUser", modifyUser);
                model.addAttribute("sessionUser", modifyUser);
                LOG.debug("log in newly created user=" + modifyUser);
            }

            LOG.debug("createNewAccount created new user user=" + modifyUser);
            message = "Enter user details below.";
            model.addAttribute("modifyUser", modifyUser);
            model.addAttribute("message", message);
            model.addAttribute("errorMessage", errorMessage);
            return "viewModifyUser";
        } else {
            LOG.debug("unknown action " + action);
            model.addAttribute("errorMessage", "unknown action " + action);
            return "home";
        }
    }

    /**
     *
     * @param model Attributes
     * @param session Session
     * @return The users page
     */
    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public String users(Model model, HttpSession session) {
        String message = "";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            model.addAttribute("errorMessage", "You must be an administrator to access user information.");
            return "home";
        }

        List<User> userList = userRepository.findAll();

        model.addAttribute("userListSize", userList.size());
        model.addAttribute("userList", userList);
        model.addAttribute("selectedPage", "users");
        return "users";
    }

    /**
     *
     * @param username The username to search for
     * @param model Attributes
     * @param session Session
     * @return If successful, the viewModifyUser page for the User
     */
    @RequestMapping(value = {"/viewModifyUser"}, method = RequestMethod.GET)
    public String modifyUser(
            @RequestParam(value = "username", required = true) String username,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        model.addAttribute("selectedPage", "home");

        LOG.debug("get viewModifyUser called for username=" + username);

        // Check secure access to modifyUser profile
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "You must be logged in to access user information.";
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            // If not an administrator you can only access your own account info
            if (!sessionUser.getUsername().equals(username)) {
                errorMessage = username + ", you can only view your own account"
                        + " information! " + sessionUser.getUsername() + " is"
                        + " currently logged in.";
                LOG.warn(errorMessage);
                model.addAttribute("errorMessage", errorMessage);
                return ("home");
            }
        }

        List<User> userList = userRepository.findByUsername(username);
        if (userList.isEmpty()) {
            LOG.error("viewModifyUser called for unknown username=" + username);
            return ("home");
        }

        User modifyUser = userList.get(0);
        model.addAttribute("modifyUser", modifyUser);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        return "viewModifyUser";
    }

    /**
     *
     * @param username User username
     * @param firstName User first name
     * @param secondName User surname
     * @param userRole User role
     * @param userEnabled User enabled status
     * @param addressLine1 User address line 1
     * @param addressLine2 User address line 2
     * @param city User city
     * @param county User county
     * @param country User country
     * @param postcode User postcode
     * @param telephone User telephone
     * @param mobile User mobile
     * @param password User password
     * @param password2 User confirmed password
     * @param cardNumber User card number
     * @param cardName User name on card
     * @param cardDate User card expiry date
     * @param saveCard User choice to save into the database (not null) or session (null)
     * @param action The action
     * @param model Attributes
     * @param session Session
     * @return If successful, the viewModifyUser page with updated information
     */
    @RequestMapping(value = {"/viewModifyUser"}, method = RequestMethod.POST)
    @Transactional
    public String updateUser(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "secondName", required = false) String secondName,
            @RequestParam(value = "userRole", required = false) String userRole,
            @RequestParam(value = "userEnabled", required = false) String userEnabled,
            @RequestParam(value = "addressLine1", required = false) String addressLine1,
            @RequestParam(value = "addressLine2", required = false) String addressLine2,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "county", required = false) String county,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "postcode", required = false) String postcode,
            @RequestParam(value = "telephone", required = false) String telephone,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "password2", required = false) String password2,
            @RequestParam(value = "cardno", required = false) String cardNumber,
            @RequestParam(value = "cardname", required = false) String cardName,
            @RequestParam(value = "carddate", required = false) String cardDate,
            @RequestParam(value = "savecard", required = false) String saveCard,
            @RequestParam(value = "action", required = false) String action,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        LOG.debug("post updateUser called for username=" + username);

        // Security check if party is allowed to access or modify this party
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "You must be logged in to access user information.";
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }
        
        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            if (!sessionUser.getUsername().equals(username)) {
                errorMessage = username + ", you can only view your own account"
                        + " information! " + sessionUser.getUsername() + " is"
                        + " currently logged in.";
                model.addAttribute("errorMessage", errorMessage);
                LOG.warn(errorMessage);
                return ("home");
            }
        }

        List<User> userList = userRepository.findByUsername(username);
        if (userList.isEmpty()) {
            errorMessage = "Update user called for unknown username:" + username + ".";
            LOG.warn(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return ("home");
        }

        User modifyUser = userList.get(0);

        // Update password if requested
        if ("updatePassword".equals(action)) {
            if (password == null || !password.equals(password2) || password.length() < 8) {
                errorMessage = "Failed the password check, please ensure it's more"
                        + " than 8 characters and you've entered the same password"
                        + " twice.";
                LOG.warn(errorMessage);

                model.addAttribute("modifyUser", modifyUser);
                model.addAttribute("errorMessage", errorMessage);
                return "viewModifyUser";

            } else {
                modifyUser.setPassword(password);
                modifyUser = userRepository.save(modifyUser);
                model.addAttribute("modifyUser", modifyUser);
                message = "Password updated for user :" + modifyUser.getUsername() + ".";
                model.addAttribute("message", message);
                return "viewModifyUser";
            }
        }

        // Else update all other properties
        // only admin User's can update modifyUser role and enabled
        if (UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            try {
                UserRole role = UserRole.valueOf(userRole);
                modifyUser.setUserRole(role);
                if (userEnabled != null && "true".equals(userEnabled)) {
                    modifyUser.setEnabled(Boolean.TRUE);
                } else {
                    modifyUser.setEnabled(Boolean.FALSE);
                }
            } catch (Exception ex) {
                errorMessage = "Cannot parse userRole" + userRole + ".";
                LOG.warn(errorMessage);
                model.addAttribute("errorMessage", errorMessage);
                return ("home");
            }
        }

        if (firstName != null) {
            modifyUser.setFirstName(firstName);
        }

        if (secondName != null) {
            modifyUser.setSecondName(secondName);
        }

        Address address = new Address();
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setCity(city);
        address.setCounty(county);
        address.setCountry(country);
        address.setPostcode(postcode);
        address.setMobile(mobile);
        address.setTelephone(telephone);

        modifyUser.setAddress(address);
        
        // Only modify the card if it's the user themselves doing it
        if (sessionUser.getUsername().equals(modifyUser.getUsername())) {
            LOG.debug("modifyUser is signed in user editing card: " + cardNumber + " " + cardName + " " + cardDate + " save to database: " + saveCard);
            if (cardNumber != null && cardName != null && cardDate != null) {
                // Check if one of the card fields are present
                if (!cardNumber.equals("") || !cardName.equals("") || !cardDate.equals("")) {
                    // Check that they're all filled, then do basic validation
                    if (!cardNumber.equals("") && !cardName.equals("") && !cardDate.equals("")) {
                        CreditCard card = new CreditCard();
                        card.setCardnumber(cardNumber);
                        card.setName(cardName);
                        card.setEndDate(cardDate);

                        CardValidationResult cardNumTest = RegexCardValidator.isValid(cardNumber);
                        if (!cardNumTest.isValid()) {
                            model.addAttribute("modifyUser", modifyUser);
                            model.addAttribute("errorMessage", "The card number is invalid.");
                            return "viewModifyUser";
                        }

                        if (card.cardDateExpiredOrError()) {
                            model.addAttribute("modifyUser", modifyUser);
                            model.addAttribute("errorMessage", "The card has an invalid or expired date.");
                            return "viewModifyUser";
                        }

                        // Only store card details in the session user, not the modifyUser
                        // which will be stored / updated in the database. Storing this
                        // information in the database is a massive security risk
                        if (saveCard != null) {
                            // Save it anyway if they checked the box
                            modifyUser.setCard(card);
                            LOG.debug("saving " + modifyUser.getUsername() + "'s card to the database");
                        }

                        sessionUser.setCard(card);

                    } else {
                        model.addAttribute("modifyUser", modifyUser);
                        model.addAttribute("errorMessage", "One of the card fields is missing.");
                        return "viewModifyUser";
                    }
                } else {
                    sessionUser.setCard(new CreditCard());
                    modifyUser.setCard(new CreditCard());
                }
            }
        }

        modifyUser = userRepository.save(modifyUser);

        model.addAttribute("modifyUser", modifyUser);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("message", "User " + modifyUser.getUsername() + " updated successfully.");
        model.addAttribute("selectedPage", "home");

        return "viewModifyUser";
    }

    /*
     * Default exception handler, catches all exceptions, redirects to friendly
     * error page. Does not catch request mapping errors
     */

    /**
     *
     * @param e Exception
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
