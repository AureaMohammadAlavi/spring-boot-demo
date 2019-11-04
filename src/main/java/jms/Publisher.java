package jms;

import java.util.Date;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Publisher {

  public static void main(String[] args) throws NamingException, JMSException {
    Context ctx = new InitialContext();

    ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
    Destination destination = (Destination) ctx.lookup("MyQueue1");

    Connection connection = connectionFactory.createConnection();
    connection.setClientID("smash-client-publisher");

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    TextMessage message = session.createTextMessage(new Date().toString());
    session.createProducer(destination).send(message);
    session.close();
    connection.close();
  }
}
