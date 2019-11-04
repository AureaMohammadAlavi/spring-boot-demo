import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpMain {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 9897);

    OutputStream outputStream = socket.getOutputStream();
    InputStream inputStream = socket.getInputStream();
    byte[] bytes = "Request 12dsdgsdgdgdfgdfgdf".getBytes(StandardCharsets.UTF_8);
    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
    dataOutputStream.writeInt(bytes.length);
    dataOutputStream.write(bytes);

    System.out.println(new String(TcpServer.readFully(inputStream), StandardCharsets.UTF_8));

    socket.close();

/*
    try (OutputStream outputStream = socket.getOutputStream()) {
      outputStream.write("hello".getBytes(StandardCharsets.UTF_8));
    }
*/

/*
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
        StandardCharsets.UTF_8))) {
      reader.lines().forEach(System.out::println);
    }*/
  }
}
