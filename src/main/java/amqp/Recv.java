package amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {

  private final static String QUEUE_NAME = "task-queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.basicQos(1);

    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
      String message = new String(delivery.getBody(), "UTF-8");
      System.out.println(" [x] Received '" + message + "', deliveryTag: " + delivery.getEnvelope()
          .getDeliveryTag());
      try {
        doWork(message);
      } finally {
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        System.out.println("[x] Done");
      }
    };
    channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
    });
  }

  private static void doWork(String message) {
    try {
      long seconds = message.chars().filter(c->c =='.').count();
      System.out.println("task will take " + seconds + " seconds to get done!");
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException ignored) {
    }
  }
}