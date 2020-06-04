import java.net.Socket;

public class ErogaServizio extends Thread {
    private final Socket socket;

    public ErogaServizio(Socket sToClient) {
        this.socket = sToClient;
    }
}
