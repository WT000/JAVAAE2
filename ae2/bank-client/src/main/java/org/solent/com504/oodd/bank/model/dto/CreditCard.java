package org.solent.com504.oodd.bank.model.dto;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Credit Card object representation
 * @author WT000
 */
@Embeddable
public class CreditCard {

    private String name;

    private String endDate;

    private String cardNumber;

    private String cvv;

    private String issueNumber = "01";

    /**
     * Constructor used when no parameter is given
     */
    public CreditCard() {
        this.cardNumber = "";
        this.name = "";
        this.endDate = "";
        this.cvv = "111";
    }

    /**
     * Constructor used when only a card number is given
     * @param cardNumber The card to set to
     */
    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
        this.name = "";
        this.endDate = "";
        this.cvv = "111";
    }

    /**
     * Constructor used when all card details are given
     * 
     * @param cardNumber The card to set to
     * @param name The name to set to
     * @param endDate The end date to set to
     * @param cvv The cvv to set to
     */
    public CreditCard(String cardNumber, String name, String endDate, String cvv) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.endDate = endDate;
        this.cvv = cvv;
    }

    /**
     * Used to tell if a card has expired or not based on MM/YY or MM/YYYY end date
     * @return a boolean depending on the result
     */
    public boolean cardDateExpiredOrError() {
        try {
            if (this.endDate.equals("")) {
                return true;
            }

            // Get the current year and month
            LocalDate currentDate = LocalDate.now();

            // Get the endDate year and month
            String[] parts = this.endDate.split("/");
            int endMonth = Integer.parseInt(parts[0]);
            Integer endYear = null;
            
            if (parts[1].length() == 2) {
                endYear = (currentDate.getYear() - (currentDate.getYear() % 100)) + Integer.valueOf(parts[1]);
            } else if (parts[1].length() == 4) {
                endYear = Integer.valueOf(parts[1]);
            } else {
                return true;
            }

            // Get the first day of the month for the endDate, then find the first day of the next month
            LocalDate endDate = LocalDate.of(endYear, endMonth, 1);
            LocalDate lastDay = endDate.with(TemporalAdjusters.lastDayOfMonth());
            LocalDate expiredDate = lastDay.plusDays(1);

            if (currentDate.isBefore(expiredDate)) {
                // Card is valid as the current date is before the expire date
                return false;
            }
            // Card is invalid as it's equal to or after the expire date
            return true;
        } catch (Exception e) {
            // Card is invalid if there's an error
            return true;
        }
    }

    /**
     *
     * @return The card name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name The card name to set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate The end date to set to (MM/YY or MM/YYYY)
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return The card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     *
     * @param cardnumber The card number to set to
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     *
     * @return The cvv
     */
    public String getCvv() {
        return cvv;
    }

    /**
     *
     * @param cvv The cvv to set to
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    /**
     *
     * @return The issue number
     */
    public String getIssueNumber() {
        return issueNumber;
    }

    /**
     *
     * @param issueNumber The issue number to set to
     */
    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Override
    public String toString() {
        return "CreditCard{" + "name=" + name + ", endDate=" + endDate + ", cardNumber=" + cardNumber + ", cvv=NOT PRINTED" + ", issueNumber=" + issueNumber + '}';
    }
}
