package amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Send {

  private final static String QUEUE_NAME = "task-queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {
      channel.queueDeclare(QUEUE_NAME, true, false, false, null);

      Scanner scanner = new Scanner(System.in);
      String message;
      do {
        System.out.print("enter task: ");
        message = scanner.nextLine();
        if (message != null) {
          channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
          System.out.println(" [x] Sent '" + message + "'");
        }
      } while (message != null);

    }
  }
}