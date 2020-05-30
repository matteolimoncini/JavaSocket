import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        try {
            InetAddress inetAddress;
            InetSocketAddress inetSocketAddress;
            inetAddress = InetAddress.getLocalHost();
            inetSocketAddress = new InetSocketAddress(inetAddress,50641);
            sToServer.connect(inetSocketAddress);
            System.out.println("CLIENT: porta:"+sToServer.getLocalPort());

            System.out.println("inserisci testo");
            InputStreamReader tastiera = new InputStreamReader(System.in);

            BufferedReader bufferedReader = new BufferedReader(tastiera);
            String frase = bufferedReader.readLine();

            OutputStream outputStream = sToServer.getOutputStream();
            outputStream.write(frase.getBytes(),0,frase.length());

            Thread.sleep(1000*10);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
