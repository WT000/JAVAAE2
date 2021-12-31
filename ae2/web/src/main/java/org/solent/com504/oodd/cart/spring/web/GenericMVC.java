package org.solent.com504.oodd.cart.spring.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.model.service.ShoppingService;
import org.solent.com504.oodd.cart.web.WebObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class GenericMVC {

    final static Logger LOG = LogManager.getLogger(GenericMVC.class);

    // this could be done with an autowired bean
    //private ShoppingService shoppingService = WebObjectFactory.getShoppingService();
    @Autowired
    ShoppingService shoppingService = null;

    // note that scope is session in configuration
    // so the shopping cart is unique for each web session
    @Autowired
    ShoppingCart shoppingCart = null;

    private User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            sessionUser = new User();
            sessionUser.setUsername("anonymous");
            sessionUser.setUserRole(UserRole.ANONYMOUS);
            session.setAttribute("sessionUser",sessionUser);
        }
        return sessionUser;
    }

    // this redirects calls to the root of our application to index.html
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "home");

        String message = "";
        String errorMessage = "";

        return "home";
    }

    @RequestMapping(value = "/about", method = {RequestMethod.GET, RequestMethod.POST})
    public String about(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        
        // used to set tab selected
        model.addAttribute("selectedPage", "about");
        return "about";
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
