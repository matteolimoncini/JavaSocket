import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerUdpEcho {
    private static final int DIM_BUFFER = 100;

    public static void main(String[] args) {
        DatagramSocket sToClient = null;
        try {
            sToClient = new DatagramSocket();
            System.out.println("SERVER: indirizzo: " + sToClient.getLocalAddress() + " porta: " + sToClient.getLocalPort());
            byte[] buffer;
            DatagramPacket dp;
            InetAddress inetAddress;
            int porta;
            String string;
            while (true) {
                buffer = new byte[DIM_BUFFER];
                dp = new DatagramPacket(buffer, DIM_BUFFER);
                sToClient.receive(dp);
                inetAddress = dp.getAddress();
                porta = dp.getPort();

                string = new String(buffer, 0, dp.getLength());

                System.out.println("RICEVUTO " + string + " FROM " + inetAddress.getHostAddress() + "; " + porta);

                sToClient.send(dp);
                System.out.println("INVIATO ECHO A " + inetAddress.getHostAddress() + "; " + porta);
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
