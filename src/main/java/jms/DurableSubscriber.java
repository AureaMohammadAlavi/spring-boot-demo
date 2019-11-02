package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DurableSubscriber {

  public static void main(String[] args) throws NamingException, JMSException {
    Context ctx = new InitialContext();

    ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
    Destination destination = (Destination) ctx.lookup("MyTopic");

    Connection connection = connectionFactory.createConnection();
//    String clientID = "smash-client-subscriber-" + System.currentTimeMillis();
    String clientID = "smash-client-subscriber-1571424632236";
    System.out.println("clientID: " + clientID);
    connection.setClientID(clientID);
    connection.start();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    MessageConsumer consumer = session
        .createDurableSubscriber((Topic) destination, "subscriptionName");

    consumeMessage(connection, session, consumer);
  }

  static void consumeMessage(Connection connection, Session session,
      MessageConsumer consumer) throws JMSException {
    Message message;
    while ((message = consumer.receive()) != null) {
      if (message instanceof TextMessage) {
        System.out.println(((TextMessage) message).getText());
      } else {
        System.out.println("Unknown message type: " + message);
      }
    }

    session.close();
    connection.close();
  }
}
