/*
 * Copyright 2022 WT000
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.solent.com504.oodd.bank.card.checker.test;

import org.solent.com504.oodd.bank.card.checker.CardValidationResult;
import org.solent.com504.oodd.bank.card.checker.RegexCardValidator;
import org.solent.com504.oodd.bank.card.checker.CardCompany;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

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

    @Test
    public void testCardFailLhun() {
        CardValidationResult result = RegexCardValidator.isValid(LHUN_FAIL_1);
        printResult(result);
        assertFalse(result.isValid());
    }

    @Test
    public void checkCalculateLuhnnchecklengh() {
        // Checks when card length is less than 13 and greater than 19
        // the length is invalid, otherwise the lengh is valid (continue)
        
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
