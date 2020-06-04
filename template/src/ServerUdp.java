import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerUdp {
    public static void main(String[] args) {
        DatagramSocket sToClient = null;
        try {
            int dimbuffer = 100;
            byte[] buffer;
            sToClient = new DatagramSocket(0);
            System.out.println("SERVER: indirizzo:" + sToClient.getLocalAddress() + "porta: " + sToClient.getPort());

            while (true) {
                buffer = new byte[dimbuffer];
                DatagramPacket dp = new DatagramPacket(buffer, dimbuffer);
                sToClient.receive(dp);

                String stringa = new String(buffer, 0, dp.getLength());
                System.out.println("CLIENT: " + dp.getAddress() + "porta: " + dp.getPort());

            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert sToClient != null;
            sToClient.close();
        }
    }
}
