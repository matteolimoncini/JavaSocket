import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ClientUser {
    private static final int DIM_BUFFER = 100;
    public static void main(String[] args) {
        byte[] buffer;
        buffer = new byte[DIM_BUFFER];
        DatagramPacket dp = new DatagramPacket(buffer, DIM_BUFFER);
        String message;
        try {

            DatagramSocket sToServer = new DatagramSocket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(),57979);
            System.out.println("CLIENT: indirizzo:"+sToServer.getLocalAddress()+ " porta:"+sToServer.getPort());

            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(tastiera);

            System.out.println("inserisci id del treno che ti interessa");
            message = br.readLine();

            buffer = message.getBytes();
            dp = new DatagramPacket(buffer,message.length());
            dp.setSocketAddress(inetSocketAddress);
            sToServer.send(dp);
            System.out.println("inviato id treno al server");

            System.out.println("aspetto dati dal server");
            buffer = new byte[DIM_BUFFER];
            dp = new DatagramPacket(buffer,DIM_BUFFER);
            sToServer.receive(dp);
            message = new String(buffer,0,dp.getLength());
            System.out.println("FROMSERVER:"+message);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
