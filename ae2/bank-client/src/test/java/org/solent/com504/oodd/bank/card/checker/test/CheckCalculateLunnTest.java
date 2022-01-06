/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.bank.card.checker.test;

import org.solent.com504.oodd.bank.card.checker.CardValidationResult;
import org.solent.com504.oodd.bank.card.checker.RegexCardValidator;
import org.solent.com504.oodd.bank.card.checker.CalculateLunnDigit;

import org.junit.Test;
import static org.junit.Assert.*;


public class CheckCalculateLunnTest {

    @Test
    public void checkCalculateLunn() {
        String pan =  "550000555555555"; // correct mastercard 5500005555555559

        String check = CalculateLunnDigit.calculateCheckDigit(pan);
        
        String ccNumber = pan+check;
        
        CardValidationResult result = RegexCardValidator.isValid(ccNumber);
        
        System.out.println("pan:"+pan
                + " ccNumber with check digit:"+ ccNumber);

        assertTrue(result.isValid());     
    }
     
    @Test
    public void checkCalculateLuhnnullpan() {
        //checks when the pan is null
        String pan = null; 
        CardValidationResult result = RegexCardValidator.isValid(pan);
        
        System.out.println(result.getError());

        assertFalse(result.isValid());
        assertEquals(result.getError(),"card cannot be null");
    }
}

