import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        String endstring="ENDSTRING";
        ServerSocket serverSocket;
        Socket toClient;
        try {
            int dimbuffer=100;
            byte[] buffer = new byte[dimbuffer];
            serverSocket= new ServerSocket(0);
            System.out.println("SERVER: indirizzo: "+serverSocket.getInetAddress()+" porta:"+serverSocket.getLocalPort());
            while (true){
                toClient=serverSocket.accept();
                System.out.println("CLIENT: indirizzo: "+toClient.getInetAddress()+" porta:"+toClient.getPort());

                InputStream fromClient = toClient.getInputStream();
                int letti = fromClient.read(buffer);

                String frase = "";
                if(letti>0) {
                    frase = new String(buffer,0,letti);
                }
                if(frase.equals(endstring)){
                    System.out.println("ENDSTRINGA");
                    break;
                }
                System.out.println("Ricevuta Stringa: " +frase+ " di "+letti+" bytes");

                toClient.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
