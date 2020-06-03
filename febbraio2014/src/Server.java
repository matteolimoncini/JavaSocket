import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    protected final static distrPrezzo distrPrezzo = new distrPrezzo(1, 9999);
    protected static final int DIM_BUFFER = 100;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(55555);
            System.out.println("SERVER: indirizzo:" + serverSocket.getInetAddress() + " porta: " + serverSocket.getLocalPort());
            Socket sToClient;
            Thread t;
            while (true) {
                sToClient = serverSocket.accept();
                t = new ErogaServizio(sToClient);
                t.start();
                System.out.println("thread partito");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
