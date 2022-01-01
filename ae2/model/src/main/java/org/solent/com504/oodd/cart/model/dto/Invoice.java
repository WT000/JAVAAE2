package org.solent.com504.oodd.cart.model.dto;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;

@Entity
public class Invoice {

    private Long id;

    private String invoiceNumber;

    private Date dateOfPurchase;

    private Double amountDue;

    //private List<ShoppingItem> purchasedItems;
    
    //private List<ShoppingItem> savedPurchasedItems;
    
    private List<InvoiceItem> savedBasketItems;

    private User purchaser;

    // Processing, Shipping, Out for Delivery, Complete
    private String currentStatus = "Processing";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

//    public List<ShoppingItem> retrieveInvoiceSavedItems() {
//        return savedPurchasedItems;
//    }
//    
//    public void setInvoiceSavedItems(List<ShoppingItem> items) {
//        this.savedPurchasedItems = items;
//    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

//    @OneToMany
//    public List<ShoppingItem> getPurchasedItems() {
//        return purchasedItems;
//    }
//    
//    public void setPurchasedItems(List<ShoppingItem> purchasedItems) {
//        this.purchasedItems = purchasedItems;
//    }
//    
    @OneToMany(cascade = CascadeType.PERSIST) 
    public List<InvoiceItem> getSavedBasketItems() {
        return savedBasketItems;
    }
    
    public void setSavedBasketItems(List<InvoiceItem> basketItems) {
        this.savedBasketItems = basketItems;
    }

    @OneToOne
    public User getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

}
