import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerIterativo {
    public static void main(String[] args) {
        Socket sToClient;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(0);
            System.out.println("SERVER: indirizzo: " + serverSocket.getInetAddress() + " porta: " + serverSocket.getLocalPort());
            while (true) {
                sToClient = serverSocket.accept();
                System.out.println("CLIENT: indirizzo: " + sToClient.getInetAddress() + " porta:" + sToClient.getPort());
                InputStream fromCl = sToClient.getInputStream();
                OutputStream toCl = sToClient.getOutputStream();
                byte[] buffer = new byte[100];
                int letti = fromCl.read(buffer);
                if (letti > 0) {
                    String message = new String(buffer, 0, letti);
                }
                toCl.write("scrivo".getBytes());
                sToClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert serverSocket != null;
                serverSocket.close();
                System.out.println("socket chiusa");
            } catch (IOException e) {
                System.err.println("can not close the socket");
                e.printStackTrace();
            }
        }
    }
}
