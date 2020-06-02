import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ClientStazione {
    private static final int DIM_BUFFER = 100;
    public static void main(String[] args) {
        byte[] buffer;
        buffer = new byte[DIM_BUFFER];
        DatagramPacket dpout = new DatagramPacket(buffer,DIM_BUFFER);
        DatagramPacket dpin;
        try {
            String message;
            DatagramSocket sToServer = new DatagramSocket();
            InetAddress inetAddress = InetAddress.getLocalHost();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress,57979);
            System.out.println();
            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(tastiera);

            System.out.println("inserisci identificatore");
            message=br.readLine();
            buffer = message.getBytes();
            dpin = new DatagramPacket(buffer,message.length());
            dpin.setSocketAddress(inetSocketAddress);
            sToServer.send(dpin);

            while (!message.equals(".")){
                buffer = new byte[DIM_BUFFER];
                System.out.println("inserisci id e ritardo");
                message=br.readLine();

                buffer = message.getBytes();

                dpin= new DatagramPacket(buffer,message.length());
                dpin.setSocketAddress(inetSocketAddress);
                sToServer.send(dpin);
                if(message.equals(".")){
                    break;
                }

                buffer = new byte[DIM_BUFFER];
                dpin= new DatagramPacket(buffer,DIM_BUFFER);
                sToServer.receive(dpin);
                message = new String(buffer,0,dpin.getLength());
                System.out.println("ricevuto dal server: "+message);
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
