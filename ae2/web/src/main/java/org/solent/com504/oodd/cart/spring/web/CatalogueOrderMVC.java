/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.spring.web;

import java.util.List;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Will
 */
@Controller
@RequestMapping("/")
public class CatalogueOrderMVC {

    final static Logger LOG = LogManager.getLogger(CatalogueOrderMVC.class);

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
    public String getCatalogue(
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "toFind", required = false) String toFind,
            @RequestParam(value = "category", required = false) String category,
            Model model,
            HttpSession session) {
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        String message = "";
        String errorMessage = "";

        List<ShoppingItem> currentItems = new ArrayList<ShoppingItem>();
        List<ShoppingItem> hiddenItems = new ArrayList<ShoppingItem>();

        // Search if that's the current action
        if ("search".equals(action)) {
            model.addAttribute("action", action);
            ShoppingItemCategory trueCat = null;

            try {
                if (!"".equals(toFind) && !"".equals(category)) {
                    // If toFind and category are provided
                    if (!"ALL".equals(category)) {
                        trueCat = ShoppingItemCategory.valueOf(category);
                        currentItems = itemRepository.findByNameIgnoreCaseContainingAndCategory(toFind, trueCat);

                    } else {
                        currentItems = itemRepository.findByNameIgnoreCaseContaining(toFind);
                    }

                } else if ("".equals(toFind) && !"".equals(category)) {
                    // If only the category is provided
                    if (!"ALL".equals(category)) {
                        trueCat = ShoppingItemCategory.valueOf(category);
                        currentItems = itemRepository.findByCategory(trueCat);
                        
                    } else {
                        currentItems = itemRepository.findAll();
                    }
                }
            } catch (Exception e) {
                errorMessage = "Illegal search criteria.";
                model.addAttribute("errorMessage", errorMessage);
                model.addAttribute("categories", ShoppingItemCategory.values());
                model.addAttribute("currentItems", currentItems);
                model.addAttribute("hiddenItems", hiddenItems);
                model.addAttribute("selectedPage", "catalogue");
                return "catalogue";
            }

        } else {
            currentItems = itemRepository.findAvailableItems();
            hiddenItems = itemRepository.findUnavailableItems();
        }

        if (currentItems.isEmpty()) {
            errorMessage = "We couldn't find any items :(";
        }

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("categories", ShoppingItemCategory.values());
        model.addAttribute("currentItems", currentItems);
        model.addAttribute("hiddenItems", hiddenItems);
        model.addAttribute("selectedPage", "catalogue");

        // If searching for a specific item, users can also see hidden items
        return "catalogue";
    }

    @RequestMapping(value = "/viewModifyItem", method = {RequestMethod.GET})
    public String getItem(
            @RequestParam(value = "itemUuid", required = true) String uuid,
            Model model,
            HttpSession session) {

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        String message = "";
        String errorMessage = "";

        List<ShoppingItem> specificItem = itemRepository.findByUuid(uuid);

        ShoppingItem itemToDisplay = specificItem.get(0);

        model.addAttribute("item", itemToDisplay);
        model.addAttribute("selectedPage", "catalogue");

        return "viewModifyItem";
    }

    public synchronized void doTransaction() {
        // Check the basket and ensure each item has a quantity less / equal to
        // the current database quantity (lookup by UUID)

        // If all is well, let the transaction go through and decrement stock
        throw new UnsupportedOperationException();
    }
}
