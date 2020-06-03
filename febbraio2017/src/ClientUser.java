import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ClientUser {
    private static final int DIM_BUFFER = 100;
    private static final String ERROR_USER = "ERROR USER";

    public static void main(String[] args) {
        byte[] buffer;
        DatagramPacket dp;
        String message = "";
        try {

            DatagramSocket sToServer = new DatagramSocket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 50526);
            System.out.println("CLIENT: indirizzo:" + sToServer.getLocalAddress() + " porta:" + sToServer.getPort());

            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(tastiera);
            while (!message.equals(".")) {
                buffer = new byte[DIM_BUFFER];
                System.out.println("inserisci id del treno che ti interessa");
                message = br.readLine();
                if (message.equals(".")) {
                    break;
                }
                buffer = message.getBytes();
                dp = new DatagramPacket(buffer, message.length());
                dp.setSocketAddress(inetSocketAddress);
                sToServer.send(dp);
                System.out.println("inviato id treno al server");

                System.out.println("aspetto dati dal server...");
                buffer = new byte[DIM_BUFFER];
                dp = new DatagramPacket(buffer, DIM_BUFFER);
                sToServer.receive(dp);
                message = new String(buffer, 0, dp.getLength());
                if (message.equals(ERROR_USER)) {
                    System.err.println("il server ha inviato un errore");
                    break;
                }
                System.out.println("FROMSERVER:" + message);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
