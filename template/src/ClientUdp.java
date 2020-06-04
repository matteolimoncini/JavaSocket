import java.io.IOException;
import java.net.*;

public class ClientUdp {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            String messageToServer = "test";
            InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 1234);
            DatagramPacket dp = new DatagramPacket(messageToServer.getBytes(), 0, messageToServer.length());
            dp.setSocketAddress(isa);
            socket.send(dp);


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert socket != null;
            socket.close();
        }
    }
}
