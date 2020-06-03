import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final distrPrezzo distrPrezzo = new distrPrezzo(1,9999);
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            Socket sToClient;

            while (true){
                sToClient = serverSocket.accept();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
