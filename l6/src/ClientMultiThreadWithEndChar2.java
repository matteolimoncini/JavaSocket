import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMultiThreadWithEndChar2 {
    private static final String END_STRING = ".";
    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        try {
            int dimbuffer=100;
            byte[] buffer = new byte[dimbuffer];
            int letti;
            InetAddress inetAddress;
            InetSocketAddress inetSocketAddress;
            InputStream fromServer;
            InputStreamReader tastiera;
            OutputStream toSrv;
            BufferedReader br;
            String echo;

            inetAddress = InetAddress.getLocalHost();
            inetSocketAddress = new InetSocketAddress(inetAddress, 50294);
            sToServer.connect(inetSocketAddress);

            System.out.println("CLIENT: porta:" + sToServer.getLocalPort());

            tastiera = new InputStreamReader(System.in);
            br = new BufferedReader(tastiera);

            while (true) {
                System.out.print("inserisci testo:");
                String frase = br.readLine();
                toSrv = sToServer.getOutputStream();
                toSrv.write(frase.getBytes(), 0, frase.length());

                if (frase.equals(END_STRING)) {
                    System.out.println("FINE INPUT");
                    break;
                }else {
                    System.out.println("messaggio: " + frase);
                }

                fromServer = sToServer.getInputStream();
                letti = fromServer.read(buffer);
                echo = new String(buffer,0,dimbuffer);
                System.out.println("RICEVUTO ECHO: "+echo);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                sToServer.close();
            } catch (IOException e) {
                System.err.println("Can not close the socket");
                e.printStackTrace();
            }
        }
    }
}