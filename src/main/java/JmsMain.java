import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.NamingException;

public class JmsMain {

  public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
// Create a new intial context, which loads from jndi.properties file:
    javax.naming.Context ctx = new javax.naming.InitialContext();

    ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
    Destination destination = (Destination) ctx.lookup("MyQueue1");

    Connection connection = connectionFactory.createConnection();
    connection.setClientID("smash-client");

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    session.run();
    connection.start();

    MessageProducer producer = session.createProducer(destination);
    Message message = session.createTextMessage("fuck you mother fucker2!");
    producer.send(message);


/*
    MessageConsumer consumer = session.createDurableSubscriber((Topic) destination, "subscriber");

*/
/*
    consumer.setMessageListener(
        message -> System.out.println("in MessageListener: message: " + message));

*//*



    Message message;
    while ((message = consumer.receive(3000)) != null) {
      System.out.println("received the following message: \n\n");
      System.out.println(message);
      System.out.println("\n\n");
    }

*/
connection.close();
  }

}
