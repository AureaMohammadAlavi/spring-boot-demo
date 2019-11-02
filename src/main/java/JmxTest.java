import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxTest {

  private static void echo(String text) {
    System.out.println(text);
  }

  public static void main(String[] args) throws IOException {
    JMXServiceURL url =
        new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:8999/jmxrmi");
    JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
    MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

    echo("\nDomains:");
    String domains[] = mbsc.getDomains();
    Arrays.sort(domains);
    for (String domain : domains) {
      echo("\tDomain = " + domain);
    }

    echo("\nMBeanServer default domain = " + mbsc.getDefaultDomain());

    echo("\nMBean count = " +  mbsc.getMBeanCount());
    echo("\nQuery MBeanServer MBeans:");
    Set<ObjectName> names =
        new TreeSet<ObjectName>(mbsc.queryNames(null, null));
    for (ObjectName name : names) {
      echo("\tObjectName = " + name);
    }

  }
}
