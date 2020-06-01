import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientStazione {
    private static final int DIM_BUFFER = 100;
    private static final String STATION_OK= "STATION OK";
    private static final String FORECAST_ERRATA = "FORECAST ERRATA";
    private static final String FORECAST_CORRETTA = "FORECAST OK";

    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        byte[] buffer = new byte[DIM_BUFFER];
        try {
            int letti;
            InetAddress inetAddress = InetAddress.getLocalHost();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress,22222);
            sToServer.connect(inetSocketAddress);
            System.out.println("CLIENT: indirizzo: "+sToServer.getLocalAddress()+" porta:"+sToServer.getLocalPort());

            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(tastiera);
            OutputStream toServer = sToServer.getOutputStream();
            InputStream fromServer = sToServer.getInputStream();


            while (true) {
                System.out.println("inserisci STATION/USER");
                String message = br.readLine();
                toServer.write(message.getBytes(), 0, message.length());

                break;
            }

            while (true) {
                System.out.println("inserisci id stazione");
                String message = br.readLine();
                toServer.write(message.getBytes(), 0, message.length());

                letti = fromServer.read(buffer);

                String msgFromSrv = new String(buffer, 0, letti);
                if(msgFromSrv.equals(STATION_OK)) break;
            }

            while (true) {
                System.out.println("inserisci previsione del tempo");
                String message = br.readLine();
                if(message.equals(".")) break;

                toServer.write(message.getBytes(), 0, message.length());
                letti = fromServer.read(buffer);
                String msgFromSrv = new String(buffer, 0, letti);
                if(msgFromSrv.equals(FORECAST_ERRATA)) System.out.println("Previsione inserita NON corretta!");
                if(msgFromSrv.equals(FORECAST_CORRETTA)) System.out.println("Previsione inserita corretta");
            }

            sToServer.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
