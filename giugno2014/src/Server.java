import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int DIM_BUFFER = 100;
    private static final String STATION = "STATION";
    private static final String USER = "USER";
    protected static forecastItem[] previsioni = new forecastItem[3];
    protected static final Character[] forecastAmmessi = {'s','v','c','p','n','-'};

    public static void main(String[] args) {
        char c='A';
        for (int i = 0; i < previsioni.length; i++) {
            previsioni[i] = new forecastItem(c++);
        }

        ServerSocket serverSocket2;
        Socket toClient;
        byte[] buffer = new byte[DIM_BUFFER];
        InputStream inputStream;

        try {
            serverSocket2 = new ServerSocket(22222);
            System.out.println("SERVER 2: indirizzo:" + serverSocket2.getInetAddress() + " porta: " + serverSocket2.getLocalPort());
            while (true) {
                toClient = serverSocket2.accept();
                System.out.println("CLIENT: indirizzo:" + toClient.getInetAddress() + " porta: " + toClient.getPort());

                inputStream = toClient.getInputStream();
                int letti = inputStream.read(buffer);
                String whichhost;
                try {
                    if (letti > 0) {
                        whichhost = new String(buffer, 0, letti);
                        switch (whichhost) {
                            case STATION:
                                Thread thread2 = new erogaServizio2(toClient);
                                thread2.start();
                                System.out.println("Thred eseguito");
                                break;
                            case USER:
                                Thread thread3 = new erogaServizio3(toClient);
                                thread3.start();
                                System.out.println("Thred eseguito");

                                break;
                            default:
                                throw new IllegalHostTypeException();
                        }
                    } else {
                        throw new IllegalHostTypeException();
                    }

                } catch (IllegalHostTypeException e) {
                    System.err.println("Tipo host non riconosciuto");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class IllegalHostTypeException extends Throwable {
    }
}
