import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ClientUdpEcho {
    private static final String END_STRING = ".";
    private static final int DIM_BUFFER=100;
    public static void main(String[] args) {
        DatagramSocket sToServer;
        DatagramPacket inputDatagramPacket;
        try{
            sToServer = new DatagramSocket();
            String hostname = "localhost";
            int port=10;
            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(tastiera);
            System.out.println("inserisci porta:");
            port = Integer.parseInt(bufferedReader.readLine());
            InetSocketAddress inetSocketAddress = new InetSocketAddress(hostname,port);
            byte[] buffer;
            String echo;
            String frase;

            do {
                System.out.println("inserisci frase:");
                frase = bufferedReader.readLine();
                if(!frase.equals(END_STRING)) {
                    DatagramPacket dp = new DatagramPacket(frase.getBytes(), frase.getBytes().length);
                    dp.setSocketAddress(inetSocketAddress);
                    sToServer.send(dp);
                    buffer = new byte[DIM_BUFFER];
                    inputDatagramPacket = new DatagramPacket(buffer, DIM_BUFFER);
                    sToServer.receive(inputDatagramPacket);

                    echo = new String(buffer, 0, inputDatagramPacket.getLength());
                    System.out.println("RICEVUTO ECHO: " + echo + " FROM " + inputDatagramPacket.getAddress() + " ; " + inputDatagramPacket.getPort());
                }
            } while (!frase.equals(END_STRING));

        }catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
