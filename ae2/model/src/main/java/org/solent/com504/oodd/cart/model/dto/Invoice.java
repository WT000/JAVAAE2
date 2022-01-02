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
    private String paymentCardNumber;
    private Date dateOfPurchase;
    private Double amountDue;
    private User purchaser;
    private List<InvoiceItem> savedBasketItems;
    private InvoiceStatus currentStatus = InvoiceStatus.PROCESSING;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    
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
    
    // Persist, so we can cascade updates (none others are needed)
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

    public InvoiceStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(InvoiceStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
    
    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    public void setPaymentCardNumber(String paymentCardNumber) {
        this.paymentCardNumber = paymentCardNumber;
    }

}
