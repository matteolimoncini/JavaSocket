import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMoreString {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket sToClient = null;
        try {
            serverSocket = new ServerSocket(0); //porta allocata dinamicamente
            System.out.println("Indirizzo: " + serverSocket.getInetAddress() + "; porta: " + serverSocket.getLocalPort());
            sToClient = serverSocket.accept();
            System.out.println("Indirizzo client: " + sToClient.getInetAddress() + "; porta: " + sToClient.getPort());

            int dimbuffer = 100;
            byte[] buffer = new byte[dimbuffer];

            while(true) {
                InputStream fromClient = sToClient.getInputStream();
                int letti = fromClient.read(buffer);
                if (letti > 0) {
                    String stampa = new String(buffer, 0, letti);

                    if (stampa.equals("0")) {
                        System.out.println("chiusura connessione in corso...");
                        sToClient.close();
                        System.out.println("socket con il client chiusa");
                        break;
                    } else {
                        System.out.println("Ricevuta stringa: " + stampa + " di " + letti + " bytes da: " + sToClient.getInetAddress() + " ; " + sToClient.getPort());
                        System.out.println(stampa);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("chiusura connessione in corso...");
                sToClient.close();
                System.out.println("socket con il client chiusa");
            } catch (Exception e) {
                System.err.println("Client error");
                e.printStackTrace();
            }
        }
    }

}
