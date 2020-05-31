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
            inetAddress = InetAddress.getLocalHost();
            inetSocketAddress = new InetSocketAddress(inetAddress, 50222);
            sToServer.connect(inetSocketAddress);
            System.out.println("CLIENT: porta:" + sToServer.getLocalPort());

            InputStreamReader tastiera = new InputStreamReader(System.in);

            BufferedReader br = new BufferedReader(tastiera);

            boolean continuable = true;
            while (continuable) {
                System.out.print("inserisci testo:");
                String frase = br.readLine();

                if (frase.equals(END_STRING)) {
                    System.out.println("FINE INPUT");
                    continuable = false;
                }else {
                    System.out.println("messaggio: " + frase);
                }

                OutputStream toSrv = sToServer.getOutputStream();
                toSrv.write(frase.getBytes(), 0, frase.length());

                if(!frase.equals(END_STRING)){
                    InputStream fromServer = sToServer.getInputStream();
                    letti = fromServer.read(buffer);
                    String echo = new String(buffer,0,dimbuffer);
                    System.out.println("RICEVUTO ECHO: "+echo);
                }
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