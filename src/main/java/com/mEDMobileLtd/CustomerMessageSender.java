/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mEDMobileLtd;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emmanuel
 */
public class CustomerMessageSender {
    
    private static String  EXCHANGE_NAME="CustomerSaved";
    public static void SendCustomerSavedMessage(String argv ){
        try {
            ConnectionFactory factory = new ConnectionFactory();            
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String message = argv;            
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println("customer no "+argv+"created");            
            channel.close();
            connection.close();
        } catch (IOException ex) {
            Logger.getLogger(CustomerMessageSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(CustomerMessageSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
