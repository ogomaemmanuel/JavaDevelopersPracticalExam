/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mEDMobileLtd.customer;

import com.mEDMobileLtd.CustomerMessageSender;
import com.mEDMobileLtd.EventsQueueApplication;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Emmanuel
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Customer customer) {
        Customer newCustomer = this.customerRepository.save(customer);
        CustomerMessageSender.SendCustomerSavedMessage(newCustomer.getIdNo());
        return ResponseEntity.created(null).build();
    }

    /**
     *
     * @param Id
     * @return
     */
    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    public ResponseEntity<?> GetCustomer(@PathVariable("Id") Long id) {        
        Optional<Customer>customer=  this.customerRepository.findById(id);
        if(customer.isPresent()){
        return ResponseEntity.ok(customer);
        }
        return ResponseEntity.noContent().build();
    }

}
