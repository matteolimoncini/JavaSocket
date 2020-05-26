import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMoreString {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket sFromClient = null;
        try {
            serverSocket = new ServerSocket(0); //porta allocata dinamicamente
            System.out.println("Indirizzo: " + serverSocket.getInetAddress() + "; porta: " + serverSocket.getLocalPort());
            sFromClient = serverSocket.accept();
            System.out.println("Indirizzo client: " + sFromClient.getInetAddress() + "; porta: " + sFromClient.getPort());

            int dimbuffer = 100;
            byte[] buffer = new byte[dimbuffer];
            InputStream fromClient = sFromClient.getInputStream();
            int letti;
            boolean continuewhile=true;
            String notifyMessage="enable";

            while(continuewhile) {
                letti = fromClient.read(buffer);
                if (letti > 0) {
                    String stampa = new String(buffer, 0, letti);

                    if (stampa.equals("0")) continuewhile = false;

                    System.out.println("Ricevuta stringa: " + stampa + " di " + letti + " bytes da: " + sFromClient.getInetAddress() + " ; " + sFromClient.getPort());
                    System.out.println(stampa);

                    //notificare il client che può inviare
                    OutputStream toCl=sFromClient.getOutputStream();
                    Thread.sleep(1000*10);
                    toCl.write(notifyMessage.getBytes(),0,notifyMessage.length());

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("chiusura connessione in corso...");
                sFromClient.close();
                System.out.println("socket con il client chiusa");
            } catch (Exception e) {
                System.err.println("Client error");
                e.printStackTrace();
            }
        }
    }

}
