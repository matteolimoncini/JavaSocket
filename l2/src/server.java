import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket sToClient;
        try {
            serverSocket = new ServerSocket(0); //porta allocata dinamicamente

            //InetAddress inetAddress = InetAddress.getLocalHost();

            //InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress,0);

            System.out.println("Indirizzo: "+serverSocket.getInetAddress()+"; porta: "+serverSocket.getLocalPort());
            sToClient = serverSocket.accept();
            System.out.println("Indirizzo client: "+sToClient.getInetAddress()+"; porta: "+sToClient.getPort());
            Thread.sleep(120*1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
