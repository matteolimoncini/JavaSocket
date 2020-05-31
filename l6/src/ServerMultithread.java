import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMultithread {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket toClient;
        try {
            serverSocket = new ServerSocket(0);
            System.out.println("SERVER: indirizzo: " + serverSocket.getInetAddress() + " porta: " + serverSocket.getLocalPort());
            while (true) {
                toClient = serverSocket.accept();
                System.out.println("CLIENT: indirizzo: " + toClient.getInetAddress() + " porta: " + toClient.getPort());

                Thread thread = new erogaservizio(toClient);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
