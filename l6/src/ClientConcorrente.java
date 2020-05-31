import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConcorrente {
    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        try {
            InetAddress inetAddress;
            InetSocketAddress inetSocketAddress;
            inetAddress = InetAddress.getLocalHost();
            inetSocketAddress = new InetSocketAddress(inetAddress, 51333);
            sToServer.connect(inetSocketAddress);
            System.out.println("CLIENT: porta:" + sToServer.getLocalPort());

            InputStreamReader tastiera = new InputStreamReader(System.in);

            BufferedReader br = new BufferedReader(tastiera);
            String frase;
            OutputStream toSrv;

            boolean continuable = true;
            while (continuable) {
                System.out.print("inserisci testo:");
                frase = br.readLine();
                System.out.println("messaggio: " + frase);

                toSrv = sToServer.getOutputStream();
                toSrv.write(frase.getBytes(), 0, frase.length());

                if (frase.equals("0")) {
                    continuable = false;
                }
            }
            //Thread.sleep(1000*10);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}