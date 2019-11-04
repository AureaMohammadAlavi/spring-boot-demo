package amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class LogEmitter {

  public static final String EXCHANGE = "smash.logs.1";

  public static void main(String[] args) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {

      channel.exchangeDeclare(EXCHANGE, "direct");

      Scanner scanner = new Scanner(System.in);
      String message;
      do {
        System.out.print("enter log: ");
        message = scanner.nextLine();
        if (message != null) {
          String severity = generateSeverity();
          System.out.println("severity: " + severity);
          channel.basicPublish(EXCHANGE, severity, null, message.getBytes(StandardCharsets.UTF_8));
        }
      } while (message != null && message.length() > 0);
    }

  }


  private static String generateSeverity() {
    String[] severity = {"info", "warn", "error"};
    return severity[(int) (Math.random() * 3)];
  }

}
