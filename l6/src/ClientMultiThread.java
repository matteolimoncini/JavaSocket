import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMultiThread {
    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        try {
            InetAddress inetAddress;
            InetSocketAddress inetSocketAddress;
            inetAddress = InetAddress.getLocalHost();
            inetSocketAddress = new InetSocketAddress(inetAddress, 50021);
            sToServer.connect(inetSocketAddress);
            System.out.println("CLIENT: porta:" + sToServer.getLocalPort());

            InputStreamReader tastiera = new InputStreamReader(System.in);

            BufferedReader br = new BufferedReader(tastiera);

            boolean continuable = true;
            while (continuable) {
                System.out.print("inserisci testo:");
                String frase = br.readLine();

                if (frase.equals("")) {
                    System.out.println("FINE INPUT");
                    continuable = false;
                } else {
                    System.out.println("messaggio: " + frase);
                }

                OutputStream toSrv = sToServer.getOutputStream();
                toSrv.write(frase.getBytes(), 0, frase.length());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sToServer.close();
            } catch (IOException e) {
                System.err.println("Can not close the socket");
                e.printStackTrace();
            }
        }
    }
}