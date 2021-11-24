/* Modifications Copyright 2018 Craig Gallen
  *********************************************
  * Original gist Copyright 2018 Ian Chan
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at

  * http://www.apache.org/licenses/LICENSE-2.0

  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
 */

package org.solent.oodd.ae1.card.checker;

/**
 * Container for validation result
 */
public class CardValidationResult {

    private boolean valid;
    private CardCompany cardType;
    private String error;
    private String cardNo;

    public CardValidationResult() {
    }

    /**
     *
     * @param cardNo The card number to contain
     * @param error The error to contain (if any)
     */
    public CardValidationResult(String cardNo, String error) {
        this.cardNo = cardNo;
        this.error = error;
    }

    /**
     *
     * @param cardNo The card number to contain
     * @param cardType The card type (CardCompany) to contain
     */
    public CardValidationResult(String cardNo, CardCompany cardType) {
        this.cardNo = cardNo;
        this.valid = true;
        this.cardType = cardType;
    }

    /**
     *
     * @return True if the card value is valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * If the card is valid
     *
     * @return An enum value corresponding to the issuer of the card
     */
    public CardCompany getCardType() {
        return cardType;
    }

    /**
     * If the card is not valid or there is an unknown issuer
     *
     * @return Error string explaining the problem
     */
    public String getError() {
        return error;
    }

    /**
     *
     * @return The number of the card
     */
    public String getCardNo() {
        return this.cardNo;
    }

    /**
     *
     * @param cardNo The card to set to
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     *
     * @param valid The validation boolean to set to
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     *
     * @param cardType The CardCompany to set to
     */
    public void setCardType(CardCompany cardType) {
        this.cardType = cardType;
    }

    /**
     *
     * @param error The contained error message to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     *
     * @return a simple message explaining the status of the card.
     */
    public String getMessage() {
        return cardNo + "    >>    " + ((valid) ? ("card: " + this.cardType) : error);
    }

    /**
     * not used but needed for jackson to generate json
     *
     * @param message The contained message to set
     */
    public void setMessage(String message) {
    }

}
