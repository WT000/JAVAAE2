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

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(Model model,
            HttpSession session) {
        String message = "You have been successfully logged out.";
        String errorMessage = "";
        // logout of session and clear
        session.invalidate();

        return "redirect:/home";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    @Transactional
    public String login(
            Model model,
            HttpSession session) {
        String message = "Please log in using your username and password.";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "User " + sessionUser.getUsername() + "is already logged in.";
            LOG.warn(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        // used to set tab selected
        model.addAttribute("selectedPage", "login");

        return "login";

    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @Transactional
    public String login(@RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "password2", required = false) String password2,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        LOG.debug("login for username=" + username);

        // get current session modifyUser 
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        model.addAttribute("selectedPage", "login");
        if (!UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "User " + sessionUser.getUsername() + "is already logged in.";
            LOG.warn(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        };

        if (username == null || username.trim().isEmpty()) {
            errorMessage = "You must enter a username.";
            model.addAttribute("errorMessage", errorMessage);
            return "login";
        }

        List<User> userList = userRepository.findByUsername(username);

        if ("login".equals(action)) {
            //todo find and add modifyUser and test password
            LOG.debug("logging in user username=" + username);
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

            message = "You've now logged into " + username + "'s account.";
            session.setAttribute("sessionUser", loginUser);

            model.addAttribute("sessionUser", loginUser);

            model.addAttribute("message", message);
            model.addAttribute("errorMessage", errorMessage);
            // used to set tab selected
            model.addAttribute("selectedPage", "home");
            return "redirect:/home";
        } else {
            model.addAttribute("errorMessage", "Unknown action requested:" + action + ".");
            LOG.error("login page unknown action requested:" + action);
            model.addAttribute("errorMessage", errorMessage);
            // used to set tab selected
            model.addAttribute("selectedPage", "home");
            return "home";
        }
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    @Transactional
    public String registerGET(@RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "password2", required = false) String password2,
            Model model,
            HttpSession session) {
        String message = "Please enter the fields below to register on the site.";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        // used to set tab selected
        model.addAttribute("selectedPage", "register");

        return "register";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @Transactional
    public String register(@RequestParam(value = "action", required = false) String action,
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

            // if already logged in - keep session modifyUser else set session modifyUser to modifyUser
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

    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    @Transactional
    public String users(Model model,
            HttpSession session) {
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

    @RequestMapping(value = {"/viewModifyUser"}, method = RequestMethod.GET)
    public String modifyuser(
            @RequestParam(value = "username", required = true) String username,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        model.addAttribute("selectedPage", "home");

        LOG.debug("get viewModifyUser called for username=" + username);

        // check secure access to modifyUser profile
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "You must be logged in to access user information.";
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            // if not an administrator you can only access your own account info
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

    @RequestMapping(value = {"/viewModifyUser"}, method = RequestMethod.POST)
    public String updateuser(
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

        // security check if party is allowed to access or modify this party
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

        // update password if requested
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

        // else update all other properties
        // only admin can update modifyUser role aand enabled
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
