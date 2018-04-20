package com.mEDMobileLtd;
import java.io.IOException;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventsQueueApplication {
    private static final String EXCHANGE_NAME = "logs";

    public EventsQueueApplication(){
        
    
    }

	public static void main(String[] args) throws InterruptedException {          
                    
		SpringApplication.run(EventsQueueApplication.class, args);
                
               
	}
        
        
        public static void initializeMessenger(String argv) throws TimeoutException{
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");            
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();            
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");            
            String message = argv;            
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");            
            channel.close();
            connection.close();
        } catch (IOException ex) {
            Logger.getLogger(EventsQueueApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        }
       
}
