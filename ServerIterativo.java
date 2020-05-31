import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerIterativo {

    public static void main(String[] args) {
        String endstring="ENDSTRING";
        ServerSocket serverSocket;
        Socket toClient;
        try {
            int dimbuffer=100;
            byte[] buffer = new byte[dimbuffer];
            int letti;
            String notification="ACCETTATO";

            serverSocket= new ServerSocket(0);
            System.out.println("SERVER: indirizzo: "+serverSocket.getInetAddress()+" porta:"+serverSocket.getLocalPort());

            while (true){
                toClient=serverSocket.accept();
                System.out.println("CLIENT: indirizzo: "+toClient.getInetAddress()+" porta:"+toClient.getPort());

                OutputStream toCl = toClient.getOutputStream();
                toCl.write(notification.getBytes(),0,notification.length());

                InputStream fromClient = toClient.getInputStream();
                letti = fromClient.read(buffer);

                String frase = "";
                if(letti>0) {
                    frase = new String(buffer,0,letti);
                }

                System.out.println("Ricevuta Stringa: " +frase+ " di "+letti+" bytes");

                toClient.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
