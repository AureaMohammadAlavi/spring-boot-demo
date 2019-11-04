package amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.GetResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class Main {

  public static final String QUEUE = "smash-queue";

  public static void main(String[] args) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE, false, false, false, null);

//      channel.basicPublish("", QUEUE, null,
//          ("first message " + new Date()).getBytes(StandardCharsets.UTF_8));
/*

      GetResponse response;
      while((response = channel.basicGet(QUEUE, true))!= null) {
        System.out.println(new String(response.getBody(), StandardCharsets.UTF_8));
      }
*/

    DeliverCallback deliverCallback = (consumerTag, message) -> {
      System.out.println("received message: \n\n");
      System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
      System.out.println("\n\n");
    };
    channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> {

    });


  }
}
