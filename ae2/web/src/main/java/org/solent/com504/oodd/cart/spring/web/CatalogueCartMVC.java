/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.spring.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.LinkedHashMap;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.dao.impl.ShoppingItemCatalogRepository;
import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.InvoiceItem;
import org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.service.ShoppingCartImpl;
import org.solent.com504.oodd.bank.client.impl.BankRestClientImpl;
import org.solent.com504.oodd.bank.card.checker.RegexCardValidator;
import org.solent.com504.oodd.bank.card.checker.CardValidationResult;
import org.solent.com504.oodd.bank.model.dto.BankAccount;
import org.solent.com504.oodd.bank.model.dto.TransactionReplyMessage;
import org.solent.com504.oodd.bank.model.dto.BankTransactionStatus;
import org.solent.com504.oodd.bank.model.dto.CreditCard;
import org.solent.com504.oodd.cart.web.PropertiesDao;
import org.solent.com504.oodd.cart.web.WebObjectFactory;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Will
 */
@Controller
@RequestMapping("/")
public class CatalogueCartMVC {

    final static Logger LOG = LogManager.getLogger(CatalogueCartMVC.class);
    private final PropertiesDao adminSettings = WebObjectFactory.getPropertiesDao();

    @Autowired
    ShoppingItemCatalogRepository itemRepository;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ShoppingCart shoppingCart = null;

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
        if (null != toFind) {
            model.addAttribute("didSearch", true);
            ShoppingItemCategory trueCat = null;

            try {
                if (!"".equals(toFind) && !"".equals(category)) {
                    // If toFind and category are provided
                    if (!"ALL".equals(category)) {
                        trueCat = ShoppingItemCategory.valueOf(category);
                        currentItems = itemRepository.findByNameIgnoreCaseContainingAndCategory(toFind, trueCat);

                    } else {
                        currentItems = itemRepository.findByNameIgnoreCaseContaining("%" + toFind + "%");
                    }

                } else if ("".equals(toFind) && !"".equals(category)) {
                    // If only the category is provided
                    if (!"ALL".equals(category)) {
                        trueCat = ShoppingItemCategory.valueOf(category);
                        currentItems = itemRepository.findByCategory(trueCat);

                    } else {
                        currentItems = itemRepository.findAllItems();
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
        model.addAttribute("currentItemsCount", currentItems.size());
        model.addAttribute("hiddenItems", hiddenItems);
        model.addAttribute("selectedPage", "catalogue");

        // If searching for a specific item, users can also see hidden items
        return "catalogue";
    }

    @RequestMapping(value = "/catalogue", method = {RequestMethod.POST})
    public String addItem(
            @RequestParam(value = "itemUuid", required = true) String itemUuid,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAtt) {

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        List<ShoppingItem> foundItems = itemRepository.findByUuid(itemUuid);

        Integer currentItemCount = null;
        if (foundItems.size() > 0) {
            // Item is found
            ShoppingItem specificItem = foundItems.get(0);

            LinkedHashMap<String, Integer> basket = shoppingCart.getBasket();

            if (basket.get(specificItem.getUuid()) == null) {
                // The item isn't in the basket
                basket.put(specificItem.getUuid(), 1);
                currentItemCount = 1;
            } else {
                // The item is in the basket
                Integer currentValue = (Integer) basket.get(specificItem.getUuid());
                basket.put(specificItem.getUuid(), currentValue + 1);
                currentItemCount = currentValue + 1;
            }

            redirectAtt.addAttribute("message", "Added " + specificItem.getName() + " to the basket (currently " + currentItemCount + ")");
        } else {
            // Item isn't found, it may have been deleted
            redirectAtt.addAttribute("warnMessage", "Couldn't add the item to the basket.");
        }

        return "redirect:/catalogue";
    }

    @RequestMapping(value = "/createItem", method = {RequestMethod.GET})
    public String getCreateCatalogueItem(Model model, HttpSession session) {
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        String message = "";
        String errorMessage = "";

        if (sessionUser.getUserRole() != UserRole.ADMINISTRATOR) {
            errorMessage = "You must be logged in as an admin to create items.";
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        }

        // Create a blank item, add it to the database and redirect to its
        // viewModifyItem page
        ShoppingItem newItem = new ShoppingItem();
        newItem.setName("New Item");
        newItem.setQuantity(0);
        newItem.setCategory(ShoppingItemCategory.OTHER);

        model.addAttribute("item", newItem);
        return "createItem";
    }

    @RequestMapping(value = "/createItem", method = {RequestMethod.POST})
    @Transactional
    public String createCatalogueItem(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "quantity", required = false) String quantity,
            Model model,
            HttpSession session) {

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        String message = "";
        String errorMessage = "";

        if (sessionUser.getUserRole() != UserRole.ADMINISTRATOR) {
            errorMessage = "You must be logged in to create items.";
            model.addAttribute(errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        }

        ShoppingItem itemToCreate = new ShoppingItem();

        // The user wants to update the item
        itemToCreate.setName(name);
        itemToCreate.setDescription(description);
        itemToCreate.setCategory(ShoppingItemCategory.valueOf(category));
        itemToCreate.setPrice(Double.valueOf(price));
        itemToCreate.setUuid(UUID.randomUUID().toString());

        if (Integer.valueOf(quantity) < 0) {
            quantity = "0";
        }

        itemToCreate.setQuantity(Integer.valueOf(quantity));

        itemRepository.save(itemToCreate);

        return "redirect:/viewModifyItem?itemUuid=" + itemToCreate.getUuid();
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

        if (specificItem.isEmpty()) {
            errorMessage = "Couldn't find the item :(";
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        }

        ShoppingItem itemToDisplay = specificItem.get(0);

        model.addAttribute("item", itemToDisplay);
        model.addAttribute("selectedPage", "catalogue");

        return "viewModifyItem";
    }

    @RequestMapping(value = "/viewModifyItem", method = {RequestMethod.POST})
    @Transactional
    public String updateItem(
            @RequestParam(value = "action", required = true) String action,
            @RequestParam(value = "uuid", required = false) String uuid,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "quantity", required = false) String quantity,
            Model model,
            HttpSession session) {

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        String message = "";
        String errorMessage = "";

        if (sessionUser.getUserRole() != UserRole.ADMINISTRATOR) {
            errorMessage = "You must be logged in to update items.";
            model.addAttribute(errorMessage);
            model.addAttribute("selectedPage", "home");
            return "home";
        }

        if ("updateItem".equals(action) || "deleteItem".equals(action)) {
            List<ShoppingItem> specificItem = itemRepository.findByUuid(uuid);

            ShoppingItem itemToEdit = specificItem.get(0);

            // The user wants to update the item
            if ("updateItem".equals(action)) {
                itemToEdit.setName(name);
                itemToEdit.setDescription(description);
                itemToEdit.setCategory(ShoppingItemCategory.valueOf(category));
                itemToEdit.setPrice(Double.valueOf(price));

                if (Integer.valueOf(quantity) < 0) {
                    quantity = "0";
                }

                itemToEdit.setQuantity(Integer.valueOf(quantity));

                itemRepository.save(itemToEdit);
                message = "Item successfully saved!";

                model.addAttribute("message", message);
                model.addAttribute("item", itemToEdit);
                model.addAttribute("selectedPage", "catalogue");

                return "viewModifyItem";
            } else {
                // The user wants to delete the item
                itemRepository.deleteByUuid(uuid);
                return "redirect:/catalogue";
            }
        } else {
            return "redirect:/catalogue";
        }
    }

    @RequestMapping(value = "/cart", method = {RequestMethod.GET})
    public String getCart(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        LinkedHashMap<String, Integer> basket = shoppingCart.getBasket();

        ArrayList<ShoppingItem> shoppingCartItems = new ArrayList<>();
        double total = 0;
        boolean foundError = false;

        // Attempt to convert each item in the current cart
        for (String itemUuid : basket.keySet()) {
            List<ShoppingItem> foundItems = itemRepository.findByUuid(itemUuid);

            if (foundItems.isEmpty()) {
                // It couldn't find the uuid, meaning the database has updated
                foundError = true;
                break;
            } else {
                // It found an item
                if (foundItems.get(0) instanceof ShoppingItem) {
                    // Get the item and set its quantity to be the appropriate
                    // amount, then add this to the running total
                    ShoppingItem foundItem = foundItems.get(0);

                    foundItem.setQuantity(basket.get(itemUuid));
                    shoppingCartItems.add(foundItems.get(0));
                    total += (foundItem.getPrice() * foundItem.getQuantity());
                } else {
                    foundError = true;
                    break;
                }
            }
        }

        if (!foundError) {
            model.addAttribute("shoppingCartItems", shoppingCartItems);
            model.addAttribute("shoppingCartTotal", total);
        } else {
            model.addAttribute("errorMessage", "Something went wrong with your cart, an item may have been deleted. Resetting your cart.");
            shoppingCart.setBasket(new LinkedHashMap<String, Integer>());
        }

        model.addAttribute("selectedPage", "cart");
        return "cart";
    }

    @RequestMapping(value = "/cart", method = {RequestMethod.POST})
    public String removeCartItem(
            @RequestParam(value = "uuid", required = true) String uuid,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAtt) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        LinkedHashMap<String, Integer> basket = shoppingCart.getBasket();

        if (basket.get(uuid) != null) {
            try {
                if (basket.get(uuid) == 1) {
                    basket.remove(uuid);
                } else {
                    Integer quantity = basket.get(uuid);
                    basket.replace(uuid, quantity - 1);
                }
            } catch (Exception e) {
                redirectAtt.addAttribute("errorMessage", "Something went wrong when removing the item.");
            }
        }

        return "redirect:/cart";
    }

    @RequestMapping(value = {"/checkout"}, method = RequestMethod.GET)
    public String prepareCheckout(Model model, HttpSession session) {
        String message = "Please confirm your details below.";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "You must be logged in to checkout.";
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }

        List<User> userList = userRepository.findByUsername(sessionUser.getUsername());

        if (userList.isEmpty()) {
            LOG.error("Checkout called for unknown username=" + sessionUser.getUsername());
            return "home";
        }

        User checkoutUser = userList.get(0);

        if (!checkoutUser.getEnabled()) {
            errorMessage = "Sorry, your account is deactivated.";
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }

        if (shoppingCart.getBasket().isEmpty()) {
            errorMessage = "There's nothing to checkout.";
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }

        model.addAttribute("checkoutUser", checkoutUser);
        model.addAttribute("sessionUser", sessionUser);
        model.addAttribute("selectedPage", "cart");
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "checkout";
    }

    @RequestMapping(value = {"/checkout"}, method = RequestMethod.POST)
    @Transactional
    public synchronized String doCheckout(
            @RequestParam(value = "firstName", required = true) String firstName,
            @RequestParam(value = "secondName", required = true) String secondName,
            @RequestParam(value = "addressLine1", required = false) String addressLine1,
            @RequestParam(value = "addressLine2", required = false) String addressLine2,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "county", required = false) String county,
            @RequestParam(value = "postcode", required = false) String postcode,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "cardno", required = true) String cardNumber,
            @RequestParam(value = "cardname", required = true) String cardName,
            @RequestParam(value = "carddate", required = true) String cardDate,
            @RequestParam(value = "cardcvv", required = true) String cardCvv,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAtt) {
        String message = "";
        String errorMessage = "";

        // security check if party is allowed to access or modify this party
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        LinkedHashMap<String, Integer> basket = shoppingCart.getBasket();
        ArrayList<ShoppingItem> shoppingCartItems = new ArrayList<>();
        double total = 0;

        boolean success = false;

        // Only attempt checkout if they're signed in, the username is found, and
        // their account is enabled
        if (!UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            List<User> userList = userRepository.findByUsername(sessionUser.getUsername());

            if (!userList.isEmpty()) {
                User checkoutUser = userList.get(0);

                if (checkoutUser.getEnabled()) {
                    LOG.debug("Attempting checkout with user=" + sessionUser.getUsername() + ".");

                    // Check card details
                    CreditCard customerCard = new CreditCard(cardNumber, cardName, cardDate, cardCvv);

                    CardValidationResult cardValidityNumber = RegexCardValidator.isValid(cardNumber);
                    boolean cardValidityDateExpired = customerCard.cardDateExpiredOrError();

                    if (!cardValidityNumber.isValid() || cardValidityDateExpired) {
                        redirectAtt.addAttribute("errorMessage", "The card is invalid or expired.");
                        return "redirect:/checkout";
                    }

                    // Check stock levels for each basket item (same code as before)
                    boolean foundError = false;

                    if (basket.isEmpty()) {
                        redirectAtt.addAttribute("errorMessage", "There's nothing to checkout.");
                        return "home";
                    }
                    
                    for (String itemUuid : basket.keySet()) {
                        List<ShoppingItem> foundItems = itemRepository.findByUuid(itemUuid);

                        if (foundItems.isEmpty()) {
                            foundError = true;
                            break;
                        } else {
                            if (foundItems.get(0) instanceof ShoppingItem) {
                                ShoppingItem foundItem = foundItems.get(0);

                                // Set the quantity and add if there's enough stock for it
                                if (foundItem.getQuantity() - basket.get(itemUuid) >= 0) {
                                    ShoppingItem dupeItem = new ShoppingItem();

                                    // Only store needed information for the invoice
                                    dupeItem.setUuid(foundItem.getUuid());
                                    dupeItem.setName(foundItem.getName());
                                    dupeItem.setPrice(foundItem.getPrice());
                                    dupeItem.setQuantity(basket.get(itemUuid));

                                    shoppingCartItems.add(dupeItem);
                                    total += (foundItem.getPrice() * basket.get(itemUuid));
                                    
                                } else {
                                    // Else, there isn't enough stock, we need to cancel
                                    redirectAtt.addAttribute("errorMessage", "We don't have enough of the following item to complete the order: " + foundItem.getName() + ".");
                                    return "redirect:/checkout";
                                }
                            } else {
                                foundError = true;
                                break;
                            }
                        }
                    }

                    if (foundError) {
                        redirectAtt.addAttribute("errorMessage", "Something's wrong with your cart, an item could have been removed from the store or your cart no longer exists.");
                        return "redirect:/checkout";
                    }

                    // All is well, attempt the purchase using current properties
                    BankRestClientImpl restClient = new BankRestClientImpl(adminSettings.getProperty("org.solent.com504.oodd.ae2.url"));
                    String bankCardRaw = adminSettings.getProperty("org.solent.com504.oodd.ae2.cardNumber");
                    CreditCard bankCard = new CreditCard(bankCardRaw);
                    String bankUsername = adminSettings.getProperty("org.solent.com504.oodd.ae2.username");
                    String bankPassword = adminSettings.getProperty("org.solent.com504.oodd.ae2.password");

                    try {
                        TransactionReplyMessage result = restClient.transferMoney(customerCard, bankCard, total, bankUsername, bankPassword);

                        if (result.getStatus() == BankTransactionStatus.SUCCESS) {
                            success = true;
                        } else {
                            redirectAtt.addAttribute(result.getMessage());
                            return "redirect:/checkout";
                        }
                    } catch (Exception e) {
                        errorMessage = "Couldn't connect to the bank (is the configured URL correct?).";
                    }
                } else {
                    errorMessage = "Your account is disabled.";
                }
            } else {
                errorMessage = "Your username isn't valid.";
            }
        } else {
            errorMessage = "You're not signed in.";
        }

        ArrayList<ShoppingItem> currentDbItems = new ArrayList<ShoppingItem>();
        
        if (success) {
            // Decrement stock
            for (ShoppingItem cartItem : shoppingCartItems) {
                List<ShoppingItem> foundItems = itemRepository.findByUuid(cartItem.getUuid());
                ShoppingItem dbItem = foundItems.get(0);
                
                dbItem.setQuantity(dbItem.getQuantity() - cartItem.getQuantity());
                itemRepository.save(dbItem);
                
                // Add the item to retrieve its details in the invoice if needed
                currentDbItems.add(dbItem);
            }
            
            // Create an invoice / order
            Invoice order = new Invoice();
            order.setAmountDue(total);
            order.setDateOfPurchase(new Date());
            order.setPurchaser(sessionUser);
            order.setInvoiceNumber(UUID.randomUUID().toString());
            order.setPaymentCardNumber(cardNumber);
            
            ArrayList<InvoiceItem> tempList = new ArrayList<InvoiceItem>();
            
            for (ShoppingItem cartItem : shoppingCartItems) {
                List<ShoppingItem> foundItems = itemRepository.findByUuid(cartItem.getUuid());
                ShoppingItem dbItem = foundItems.get(0);
                
                InvoiceItem toAdd = new InvoiceItem(dbItem, basket.get(cartItem.getUuid()));
                tempList.add(toAdd);
            }
            
            order.setSavedBasketItems(tempList);
            
            LOG.debug("Saving new invoice: current items=" + currentDbItems + ", saved items=" + shoppingCartItems);
            invoiceRepository.save(order);
            
            // Clear the basket and redirect to the created invoice / order
            basket.clear();
            return "redirect:/viewModifyOrder?invoiceNumber=" + order.getInvoiceNumber();
        }

        // Display a relevant error msg on the checkout page
        redirectAtt.addAttribute("errorMessage", errorMessage);
        return "redirect:/checkout";
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
