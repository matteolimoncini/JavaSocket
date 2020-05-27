import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ClientUdp {
    public static void main(String[] args) {
        DatagramSocket sToServer;

        try {

            //parametri di default
            String hostName = "localhost";
            int port = 57685;

            if(args.length!=0){
                hostName = args[0];
                port = Integer.parseInt(args[1]);
                if (port <= 0) throw new IllegalArgumentException("numero porta non valido");
                if (args.length != 2) throw new IllegalArgumentException("numero parametri non corretto");
            }

            sToServer = new DatagramSocket();

            InetSocketAddress isa = new InetSocketAddress(hostName,port);

            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(tastiera);
            String frase = br.readLine();
            byte[] buffer =frase.getBytes();

            DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
            dp.setSocketAddress(isa);
            sToServer.send(dp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
