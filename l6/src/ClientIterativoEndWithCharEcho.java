import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientIterativoEndWithCharEcho {
    private static final String END_STRING = ".";

    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        int dimbuffer = 100;
        byte[] buffer = new byte[dimbuffer];
        String recived = "";
        InetAddress inetAddress;
        InetSocketAddress inetSocketAddress;
        InputStream fromSrv;
        InputStreamReader tastiera;
        String frase = "";
        OutputStream toServer;
        BufferedReader bufferedReader;
        try {
            inetAddress = InetAddress.getLocalHost();
            inetSocketAddress = new InetSocketAddress(inetAddress, 50656);

            sToServer.connect(inetSocketAddress);
            System.out.println("CLIENT: indirizzo:" + sToServer.getInetAddress()+" porta: "+sToServer.getLocalPort());

            tastiera = new InputStreamReader(System.in);
            bufferedReader = new BufferedReader(tastiera);

            while (true) {
                System.out.println("Inserisci testo");
                frase = bufferedReader.readLine();

                toServer = sToServer.getOutputStream();
                toServer.write(frase.getBytes());
                if(frase.equals(END_STRING)){
                    break;
                }
                fromSrv = sToServer.getInputStream();
                int letti = fromSrv.read(buffer);

                if (letti > 0) {
                    recived = new String(buffer, 0, letti);
                    System.out.println("ECHO: " + recived);
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
