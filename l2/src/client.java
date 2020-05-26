import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        try{
            InetAddress ia;                     //ip server
            InetSocketAddress isa;              //socket address server
            ia = InetAddress.getLocalHost();
            //System.out.println("Indirizzo: " + ia.getHostName() + " --> " + ia.getHostAddress());
            isa = new InetSocketAddress(ia,54801);          //porta dest ip dest
            sToServer.connect(isa);                              //la connect richiama lei la bind
            System.out.println("porta allocata: "+sToServer.getLocalPort());
            Thread.sleep(120*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
