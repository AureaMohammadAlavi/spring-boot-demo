package amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class LogConsumer {

  public static final String EXCHANGE = "smash.logs.1";

  public static void main(String[] args) throws IOException, TimeoutException {
    if (args.length < 1 || args[0].length() < 1) {
      System.out.println("Usage: LogConsumer level");
      System.exit(1);
    }

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE, "direct");
    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(queueName, EXCHANGE, args[0]);

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
      String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
      System.out.println(message);
    };
    channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
    });

  }
}
