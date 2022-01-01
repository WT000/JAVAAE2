/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.spring.web;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Will
 */
@Controller
@RequestMapping("/")
public class OrdersMVC {

    final static Logger LOG = LogManager.getLogger(OrdersMVC.class);

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

    @RequestMapping(value = "/viewModifyOrder", method = {RequestMethod.GET})
    public String getOrder(
            @RequestParam(value = "invoiceNumber", required = true) String invoiceNumber,
            Model model,
            HttpSession session) {

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

        model.addAttribute("invoice", invoiceToDisplay);
        model.addAttribute("selectedPage", "orders");

        return "viewModifyOrder";
    }
}