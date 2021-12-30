/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.spring.web;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.dao.impl.ShoppingItemCatalogRepository;
import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Will
 */
@Controller
@RequestMapping("/")
public class CatalogueMVC {
    
    final static Logger LOG = LogManager.getLogger(CatalogueMVC.class);
    
    @Autowired
    ShoppingItemCatalogRepository itemRepository;
    
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
    
    @RequestMapping(value = "/catalogue", method = {RequestMethod.GET})
    public String getCatalogue(Model model, HttpSession session) {
        String message = "";
        String errorMessage = "";
        
        List<ShoppingItem> currentItems = itemRepository.findAll();
        LOG.debug("found items: " + currentItems);
        
        if (currentItems.isEmpty()) {
            errorMessage = "We couldn't find any items :(";
        }
        
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("currentItems", currentItems);
        model.addAttribute("selectedPage", "catalogue");
        
        // Find unavailable items (quantity = 0) if getUser role is admin, 
        // otherwise make it an empty list
        return "catalogue";
    }
}
