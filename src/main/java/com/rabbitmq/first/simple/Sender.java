package com.rabbitmq.first.simple;

import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		try(Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()){
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello Receiver";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

			for(int i=1; i <= 5000; i++) {
				message = "Message-"+i;
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
			}
		}
	}
}
