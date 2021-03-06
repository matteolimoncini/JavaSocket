import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerUdp {
    public static void main(String[] args) {
        try {

            DatagramSocket sToClient = new DatagramSocket();
            System.out.println("SERVER: indirizzo: "+sToClient.getLocalAddress()+"; porta: "+sToClient.getLocalPort());

            int dimbuffer=100;
            long time=System.currentTimeMillis();
            long end=time+30000;
            while(System.currentTimeMillis()<end) {
                byte[] buffer = new byte[dimbuffer];
                DatagramPacket dp = new DatagramPacket(buffer, dimbuffer);
                sToClient.receive(dp);

                String stringa = new String(buffer, 0, dp.getLength());
                System.out.println("ricevuto CLIENT: " + stringa);

                //elaborazione stringa
                //Thread.sleep(1000*10);

                InetAddress ia = dp.getAddress();
                int porta = dp.getPort();
                System.out.println("CLIENT: Indirizzo: " + ia.getHostAddress() + "; porta: " + porta);
            }
            sToClient.close();      //chiude entrambi i lati
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
