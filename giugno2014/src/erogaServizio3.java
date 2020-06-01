import java.net.Socket;

public class erogaServizio3 extends Thread {
    private Socket socket;
    public erogaServizio3(Socket toClient) {
      this.socket=toClient;
    }

    @Override
    public void run() {

    }
}
