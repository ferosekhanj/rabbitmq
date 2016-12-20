package com.ferose;

import java.io.*;
import com.rabbitmq.client.*;

public class TaskServer {

    public static void main(String[] args)throws Exception{

	String QUEUE_NAME = "task_queue";
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");

	final Connection connection = factory.newConnection();
	final Channel    channel    = connection.createChannel();
	channel.queueDeclare(QUEUE_NAME,true,false,false,null); // create a non durable queue
	System.out.println("Waiting for work ..");

	Consumer consumer = new DefaultConsumer(channel){
		@Override
		public void handleDelivery(String consumerTag,Envelope envelope,
					   AMQP.BasicProperties properties,byte[] body)
		    throws IOException{
		    String message = new String(body,"UTF-8");
		    System.out.println("Received =>"+message);
		    channel.basicAck(envelope.getDeliveryTag(), false);
    
		}
	    };
	channel.basicConsume(QUEUE_NAME,false,consumer);

	
    }
}
