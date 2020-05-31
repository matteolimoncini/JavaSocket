import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerIterativoEndWithCharEcho {
    private static final String END_STRING = ".";
    private static final int DIMBUFFER = 100;

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket toClient = null;
        try {
            int letti;
            String string = "";
            byte[] buffer;
            OutputStream toCl;
            InputStream fromCl;

            serverSocket = new ServerSocket(0);
            System.out.println("SERVER: indirizzo: " + serverSocket.getInetAddress() + " porta: " + serverSocket.getLocalPort());
            while (true) {
                toClient = serverSocket.accept();
                System.out.println("CLIENT: indirizzo: " + toClient.getInetAddress() + " porta: " + toClient.getPort());
                while (true) {
                    toCl = toClient.getOutputStream();
                    fromCl = toClient.getInputStream();

                    buffer = new byte[DIMBUFFER];
                    letti = fromCl.read(buffer);

                    if (letti > 0) {
                        string = new String(buffer, 0, letti);
                        System.out.println("ricevuta:" + string + " di " + letti + " bytes FROM " + toClient.getInetAddress() + "; " + toClient.getPort());
                    }
                    toCl.write(string.getBytes());
                    if (string.equals(END_STRING)) {
                        System.out.println("RICEVUTO CHIUSURA CONNESSIONE FROM " + toClient.getInetAddress() + "; " + toClient.getPort());
                        toClient.close();
                        System.out.println("CHIUSURA CONNESSIONE FROM " + toClient.getInetAddress() + "; " + toClient.getPort());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert toClient != null;
                toClient.close();
            } catch (IOException e) {
                System.err.println("can not close socket");
                e.printStackTrace();
            }
        }
    }
}
