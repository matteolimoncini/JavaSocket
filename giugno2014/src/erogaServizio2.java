import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class erogaServizio2 extends Thread {
    private static final int DIM_BUFFER = 100;
    private static final String STATION = "STATION";
    private static final String FORECAST_ERRATA = "FORECAST ERRATA";
    private static final String STATION_OK = "STATION OK";
    private static final String FORECAST_CORRETTA = "FORECAST OK";
    private Socket socket;

    public erogaServizio2(Socket toClient) {
        this.socket = toClient;
    }

    @Override
    public void run() {
        byte[] buffer;
        try {
            InputStream fromCl = socket.getInputStream();
            OutputStream toCl;
            toCl = socket.getOutputStream();

            int letti;
            int i = 0;

            boolean identificato = false;
            while (!identificato) {
                buffer = new byte[DIM_BUFFER];
                letti = fromCl.read(buffer);

                if (letti > 0) {
                    String message;
                    message = new String(buffer, 0, letti);
                    System.out.println("RICEVUTA STRINGA: " + message + " di " + letti + " byte da " + socket.getInetAddress() + " ; " + socket.getPort());
                    String idrecord;

                    for (i = 0; i < Server.previsioni.length; i++) {
                        idrecord = String.valueOf(Server.previsioni[i].getId());
                        if (message.equals(idrecord)) {
                            break;
                        }
                    }
                } else {
                    System.err.println("numero byte letto <= 0");
                    break;
                }

                if (i == Server.previsioni.length - 1) {
                    System.err.println("id errato");
                    String idError = "ID ERRATO";
                    toCl.write(idError.getBytes(), 0, idError.length());
                } else {
                    identificato = true;
                    toCl.write(STATION_OK.getBytes(), 0, STATION_OK.length());
                    System.out.println("scritto station ok");
                }
            }

            while (true) {

                buffer = new byte[DIM_BUFFER];
                letti = fromCl.read(buffer);

                if (letti > 0) {
                    String message;
                    message = new String(buffer, 0, letti);
                    System.out.println("RICEVUTA STRINGA: " + message + " di " + letti + " byte da " + socket.getInetAddress() + " ; " + socket.getPort());

                    char c = message.charAt(0);

                    if (Arrays.asList(Server.forecastAmmessi).contains(c)) {
                        Server.previsioni[i].setTempo(c);
                        System.out.println("ho accetto una previsione");
                        toCl.write(FORECAST_CORRETTA.getBytes(), 0, FORECAST_CORRETTA.length());
                    } else {
                        System.err.println("forecast errata");
                        toCl.write(FORECAST_ERRATA.getBytes(), 0, FORECAST_ERRATA.length());
                    }
                } else {
                    System.err.println("numero byte letto <= 0");
                    break;
                }

            }

        } catch (IOException e) {
            System.err.println("Errore di I/O");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.err.println("socket chiusa");
            } catch (IOException e) {
                System.err.println("can not close the socket");
                e.printStackTrace();
            }
        }
    }
}
