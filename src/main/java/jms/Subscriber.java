package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Subscriber {

  public static void main(String[] args) throws JMSException, NamingException {

    Context ctx = new InitialContext();

    ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
    Destination destination = (Destination) ctx.lookup("MyQueue");

    Connection connection = connectionFactory.createConnection();
    connection.setClientID("smash-client-subscriber-" + System.currentTimeMillis());
    connection.start();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    MessageConsumer consumer = session.createConsumer(destination);
    DurableSubscriber.consumeMessage(connection, session, consumer);
  }
}
