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
package org.solent.com504.oodd.cart.model.dto;

import javax.persistence.Embeddable;

/**
 * Address object which is embedded in a User
 * @author WT000
 */
@Embeddable
public class Address {
    private String addressLine1;

    private String addressLine2;
    
    private String county;
    
    private String city;

    private String postcode;

    private String mobile;

    private String telephone;

    private String country;

    /**
     *
     * @return Current addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     *
     * @param addressLine1 addressLine1 to set to
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     *
     * @return Current addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     *
     * @param addressLine2 addressLine2 to set to
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     *
     * @return Current postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     *
     * @param postcode postcode to set to
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     *
     * @return Current mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile mobile to set to
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return Current telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     *
     * @param telephone telephone to set to
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     *
     * @return Current country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country country to set to
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return Current county
     */
    public String getCounty() {
        return county;
    }

    /**
     *
     * @param county county to set to
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     *
     * @return Current city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city city to set to
     */
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", county=" + county + ", city=" + city + ", postcode=" + postcode + ", mobile=" + mobile + ", telephone=" + telephone + ", country=" + country + '}';
    }
}
