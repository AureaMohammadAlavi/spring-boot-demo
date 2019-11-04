import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpServer {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(1234);
    Socket socket;
    while ((socket = serverSocket.accept()) != null) {
      try {
        socket.setSoTimeout(1000);
        String response = new String(readData(socket.getInputStream()), StandardCharsets.UTF_8)
            .toUpperCase();
        System.out.println("sending '" + response + "' ...");
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
      } catch (Throwable e) {
        e.printStackTrace();
      } finally {
        socket.close();
      }
    }
  }


  public static byte[] readFully(InputStream inputStream) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    byte[] buffer = new byte[1024];
    int cb;
    while ((cb = inputStream.read(buffer)) != -1) {
      baos.write(buffer, 0, cb);
    }
    return baos.toByteArray();
  }

  public static byte[] readData(InputStream inputStream) throws IOException {
    DataInputStream dataInputStream = new DataInputStream(inputStream);
    int length = dataInputStream.readInt();

    byte[] data = new byte[length];
    if (inputStream.read(data) != length) {
      throw new IllegalStateException("invalid request");
    }
    return data;
  }

}
