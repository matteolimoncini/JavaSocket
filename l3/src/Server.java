import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket sToClient = null;
        try {
            serverSocket = new ServerSocket(0); //porta allocata dinamicamente
            System.out.println("Indirizzo: "+serverSocket.getInetAddress()+"; porta: "+serverSocket.getLocalPort());
            sToClient = serverSocket.accept();
            System.out.println("Indirizzo client: "+sToClient.getInetAddress()+"; porta: "+sToClient.getPort());
            //Thread.sleep(120*1000);

            int dimbuffer =100;
            byte[] buffer = new byte[dimbuffer];

            InputStream fromClient = sToClient.getInputStream();
            int letti = fromClient.read(buffer);

            String stampa = new String(buffer,0,letti);
            System.out.println("Ricevuta stringa: "+stampa+" di "+letti+" bytes");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                sToClient.close();
            }catch (Exception e){
                System.err.println("Client error");
                e.printStackTrace();
            }
        }
    }
}

