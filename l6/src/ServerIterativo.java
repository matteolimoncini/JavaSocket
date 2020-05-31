import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerIterativo {

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket toClient = null;
        try {
            int dimbuffer = 100;
            byte[] buffer = new byte[dimbuffer];
            int letti;
            String notification = "ACCETTATO";
            OutputStream toCl;
            InputStream fromClient;
            String frase = "";

            serverSocket = new ServerSocket(0);
            System.out.println("SERVER: indirizzo: " + serverSocket.getInetAddress() + " porta:" + serverSocket.getLocalPort());

            while (true) {
                toClient = serverSocket.accept();
                System.out.println("CLIENT: indirizzo: " + toClient.getInetAddress() + " porta:" + toClient.getPort());

                toCl = toClient.getOutputStream();
                toCl.write(notification.getBytes(), 0, notification.length());

                fromClient = toClient.getInputStream();
                letti = fromClient.read(buffer);

                if (letti > 0) {
                    frase = new String(buffer, 0, letti);
                }

                System.out.println("Ricevuta Stringa: " + frase + " di " + letti + " bytes");

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert toClient != null;
                toClient.close();
            } catch (IOException e) {
                System.err.print("can not close the socket");
                e.printStackTrace();
            }

        }
    }
}
