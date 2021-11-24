/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.oodd.ae1.card.checker;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 * Tests of various cards
 * @author nastaransharifisadr 
 */
public class TestRegexCardValidator {

    public static final String VALID_VISA_1 = "4444444444444448";
    public static final String VALID_MASTERCARD_1 = "5500005555555559";
    public static final String VALID_AMEX_1 = "371449635398431";
    public static final String VALID_DINERS_1 = "36438936438936";
    public static final String VALID_DISCOVER_1 = "6011016011016011";
    public static final String VALID_JCB_1 = "3566003566003566";
    public static final String LHUN_FAIL_1 = "1111111111111111";
    public static final String INVALID_STRING = "abcdabcdabcdabcd";

    @Test
    public void testCardVisa() {
        CardValidationResult result = RegexCardValidator.isValid(VALID_VISA_1);
        printResult(result);
        assertTrue(result.isValid());
        assertEquals(result.getCardType(),CardCompany.VISA );
    }
    //added by nastaran
    @Test
    public void testCardVisafail() {
        CardValidationResult result = RegexCardValidator.isValid("4454444444444448");
        printResult(result);
        assertFalse(result.isValid());
    }
   
    @Test
    public void testCardMastercard() {
        CardValidationResult result = RegexCardValidator.isValid(VALID_MASTERCARD_1);
        printResult(result);
        assertTrue(result.isValid());
        assertEquals(result.getCardType(),CardCompany.MASTERCARD );
    }
    //added by nastaran
    @Test
    public void testCardMastercardfail() {
        CardValidationResult result = RegexCardValidator.isValid("5590005555555559");
        printResult(result);
        assertFalse(result.isValid());
        
    }

    @Test
    public void testCardAmex() {
        CardValidationResult result = RegexCardValidator.isValid(VALID_AMEX_1);
        printResult(result);
        assertTrue(result.isValid());
        assertEquals(result.getCardType(),CardCompany.AMEX );
    }
    //added by nastaran
    @Test
    public void testCardAmexfail() {
        CardValidationResult result = RegexCardValidator.isValid("37/449635398431");
        printResult(result);
        assertFalse(result.isValid());
    }

    @Test
    public void testCardDiners() {
        CardValidationResult result = RegexCardValidator.isValid(VALID_DINERS_1);
        printResult(result);
        assertTrue(result.isValid());
        assertEquals(result.getCardType(),CardCompany.DINERS );
    }
    //added by nastaran
    @Test
    public void testCardDinersfail() {
        CardValidationResult result = RegexCardValidator.isValid("39438936438936");
        printResult(result);
        assertFalse(result.isValid());
    }

    @Test
    public void testCardDiscover() {
        CardValidationResult result = RegexCardValidator.isValid(VALID_DISCOVER_1);
        printResult(result);
        assertTrue(result.isValid());
        assertEquals(result.getCardType(),CardCompany.DISCOVER);
    }
    //added by nastaran
    @Test
    public void testCardDiscoverfail() {
        CardValidationResult result = RegexCardValidator.isValid("6029016011016011");
        printResult(result);
        assertFalse(result.isValid());
    }
    
    @Test
    public void testCardJcb() {
        CardValidationResult result = RegexCardValidator.isValid(VALID_JCB_1);
        printResult(result);
        assertTrue(result.isValid());
        assertEquals(result.getCardType(),CardCompany.JCB );
    }
    //added by nastaran
    @Test
    public void testCardJcbfail() {
        CardValidationResult result = RegexCardValidator.isValid("3866003566003566");
        printResult(result);
        assertFalse(result.isValid());
    }

    @Test
    public void testCardWrongString() {
        CardValidationResult result = RegexCardValidator.isValid(INVALID_STRING);
        printResult(result);
        assertFalse(result.isValid());
    }
    //added by nastaran
    @Test
    public void testCardFailLhun() {
        CardValidationResult result = RegexCardValidator.isValid(LHUN_FAIL_1);
        printResult(result);
        assertFalse(result.isValid());
    }
    //added by nastaran
    @Test
    public void checkCalculateLuhnnchecklengh() {
        //checks when card length is less than 13 and greater than 19 then 
        //the length is invalid otherwise the lengh is valid (continue)
        
        for (int i=0; i<50;i++){  
            if (i>=13 && i<=19) continue;
            char[] chars = new char[i];
            Arrays.fill(chars, '5');
            String pan = new String(chars);
            CardValidationResult result = RegexCardValidator.isValid(pan);     
        
            System.out.println(result.getError());
            assertFalse(result.isValid());
            assertEquals(result.getError(), "failed length check");
        }
    }

    /**
     * Check a card number and print the result
     *
     * @param cardIn
     */
    private void printResult(CardValidationResult result) {
        System.out.println(result.isValid() + " : " + (result.isValid() ? result.getCardType().getIssuerName() : "") + " : " + result.getMessage());
    }
}
