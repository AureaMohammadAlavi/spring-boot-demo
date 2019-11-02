import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class JdbcTest {

  public static void main(String[] args) throws Exception {
    Class.forName("com.smartbear.servicev.jdbc.driver.JdbcVirtDriver");
    Connection connection = DriverManager.getConnection("jdbc:servicev://localhost:10080");
    PreparedStatement statement = connection.prepareStatement("Select manager.* From manager");

    ResultSet resultSet = statement.executeQuery();
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

    while (resultSet.next()) {
      for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {

        System.out.print(resultSetMetaData.getColumnName(i));
        System.out.print(": ");
        System.out.print(resultSet.getString(i));
        System.out.print(", ");
      }
      System.out.println();
    }
    connection.close();
    System.exit(0);
  }

}
