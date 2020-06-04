import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMultithread {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(0);
            Socket sToClient;
            while (true) {
                sToClient = serverSocket.accept();
                Thread t = new ErogaServizio(sToClient);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert serverSocket != null;
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
