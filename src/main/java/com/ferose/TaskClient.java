package com.ferose;

import java.io.*;
import com.rabbitmq.client.*;

public class TaskClient{

    static final String QUEUE_NAME = "task_queue";
    public static void main(String[] argv) throws Exception
    {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();
	channel.queueDeclare(QUEUE_NAME, true, false, false, null);
	String message = argv[0];
	System.out.println("Sending message =>"+message);
	channel.basicPublish("",
			     QUEUE_NAME,
			     MessageProperties.PERSISTENT_TEXT_PLAIN,
			     message.getBytes("UTF-8"));
	System.out.println(" [x] Sent '" + message + "'");
	channel.close();
	connection.close();
    }
}
