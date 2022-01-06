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
package org.solent.com504.oodd.cart.dao.impl;

import java.util.List;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Invoice database
 * @author WT000
 */
@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice,Long>{

    /**
     *
     * @param username The invoice to find by purchaser username
     * @return A list of invoices which match the criteria
     */
    @Query("select i from Invoice i where UPPER(i.purchaser.username) = UPPER(?1) order by i.id desc")
    public List<Invoice> findByPurchaserUsername(String username);
    
    /**
     *
     * @return All invoices in date order
     */
    @Query("select i from Invoice i order by i.id desc")
    public List<Invoice> findAllInvoices();
    
    /**
     *
     * @param invoiceNumber The invoice to find by invoice number
     * @return A list of invoices which match the criteria
     */
    public List<Invoice> findByInvoiceNumber(String invoiceNumber);
}