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

