import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientUser {
    private static final int DIM_BUFFER = 100;
    private static final String STATION_OK = "STATION OK";
    private static final String FORECAST_ERRATA = "FORECAST ERRATA";
    private static final String FORECAST_CORRETTA = "FORECAST OK";
    private static final String FEEDBACK_OK = "FEEDBACK OK";
    private static final String FEEDBACK_ERROR = "FEEDBACK ERRATO";

    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        byte[] buffer;
        try {
            buffer = new byte[DIM_BUFFER];
            int letti;
            InetAddress inetAddress = InetAddress.getLocalHost();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, 22222);
            sToServer.connect(inetSocketAddress);
            System.out.println("CLIENT: indirizzo: " + sToServer.getLocalAddress() + " porta:" + sToServer.getLocalPort());

            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(tastiera);
            InputStream fromServer = sToServer.getInputStream();
            OutputStream toServer = sToServer.getOutputStream();
            String message;

            System.out.println("inserisci STATION/USER");
            message = br.readLine();
            toServer.write(message.getBytes(), 0, message.length());
            System.out.println("inviato al server Station/user");

            System.out.println("in attesa delle previsioni ...");

            try {
                letti = fromServer.read(buffer);
            } catch (IOException e) {
                throw new ReadException();
            }

            if (letti > 0) {
                String msgFromServer = new String(buffer, 0, letti);
                System.out.println("previsione ricevuta");
                System.out.println(msgFromServer);
            } else {
                throw new ReadException();
            }

            int count = 0;
            while (count < 3) {
                buffer = new byte[DIM_BUFFER];
                System.out.println("inserisci idstazione feedback");
                message = br.readLine();
                toServer.write(message.getBytes(), 0, message.length());
                System.out.println("inviato al server iddestinazione fedeback");

                try {
                    letti = fromServer.read(buffer);
                } catch (IOException e) {
                    throw new ReadException();
                }
                if (letti <= 0) {
                    throw new ReadException();
                }
                String msgFromServer = new String(buffer, 0, letti);

                if (msgFromServer.equals(FEEDBACK_OK)) {
                    count++;
                    System.out.println("inserito feedback corretto");
                }
                if (msgFromServer.equals(FEEDBACK_ERROR)) {
                    System.out.println("inserito feedback errato");
                }
            }


        } catch (ReadException e) {
            System.err.println("error nella read");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ReadException extends Throwable {
    }
}
