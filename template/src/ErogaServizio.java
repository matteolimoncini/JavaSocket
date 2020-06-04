import java.io.IOException;
import java.net.Socket;

public class ErogaServizio extends Thread {
    private final Socket socket;

    public ErogaServizio(Socket sToClient) {
        this.socket = sToClient;
    }

    @Override
    public void run() {
        try {

        } catch (Exception e) {

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("can not close the socket");
                e.printStackTrace();
            }
        }
    }
}

