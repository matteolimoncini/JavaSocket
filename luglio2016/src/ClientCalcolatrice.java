import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientCalcolatrice {
    private static final int DIM_BUFFER = 100;
    private static final String END_CLIENT = ".";
    private static final String vald = "VALIDO";

    public static void main(String[] args) {
        Socket toServer;
        toServer = new Socket();
        InetAddress inetAddress;
        InetSocketAddress inetSocketAddress;
        BufferedReader bufferedReader;
        InputStreamReader tastiera;
        OutputStream outputStream;
        InputStream inputStream;
        String fromServer;
        String line;
        int letti;
        try {
            byte[] buffer = new byte[DIM_BUFFER];
            inetAddress = InetAddress.getLocalHost();
            inetSocketAddress = new InetSocketAddress(inetAddress, 38663);
            toServer.connect(inetSocketAddress);
            System.out.println("CLIENT: indirizzo: " + toServer.getLocalAddress() + " porta: " + toServer.getPort());

            tastiera = new InputStreamReader(System.in);
            bufferedReader = new BufferedReader(tastiera);
            outputStream = toServer.getOutputStream();
            inputStream = toServer.getInputStream();
            boolean bool = true;
            boolean bool1 = true;
            boolean bool2 = true;
            while (true) {

                while (bool) {
                    System.out.println("inserisci operatore");
                    line = bufferedReader.readLine();
                    outputStream.write(line.getBytes(), 0, line.length());
                    if (line.equals(END_CLIENT)) break;

                    letti = inputStream.read(buffer);
                    fromServer = new String(buffer, 0, letti);
                    if (fromServer.equals(vald)) {
                        bool = false;
                    }
                }

                while (bool1) {
                    System.out.println("inserisci operando1");
                    line = bufferedReader.readLine();
                    outputStream.write(line.getBytes(), 0, line.length());
                    if (line.equals(END_CLIENT)) break;

                    letti = inputStream.read(buffer);
                    fromServer = new String(buffer, 0, letti);
                    if (fromServer.equals(vald)) {
                        bool1 = false;
                    }
                }

                while (bool2) {
                    System.out.println("inserisci operando2");
                    line = bufferedReader.readLine();
                    outputStream.write(line.getBytes(), 0, line.length());
                    if (line.equals(END_CLIENT)) break;

                    letti = inputStream.read(buffer);
                    fromServer = new String(buffer, 0, letti);
                    if (fromServer.equals(vald)) {
                        bool2=false;
                    }
                }

                bool=bool1=bool2=true;
                letti = inputStream.read(buffer);
                fromServer = new String(buffer, 0, letti);
                System.out.println("risultato: "+fromServer);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}