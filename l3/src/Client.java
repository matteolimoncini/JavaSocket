import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        try{
            InetAddress ia;                     //ip server
            InetSocketAddress isa;              //socket address server
            ia = InetAddress.getLocalHost();
            isa = new InetSocketAddress(ia,64827);          //porta dest ip dest
            sToServer.connect(isa);                              //la connect richiama lei la bind
            System.out.println("porta allocata: "+sToServer.getLocalPort());

            System.out.print("inserisci testo:");
            InputStreamReader tastiera = new InputStreamReader(System.in);

            BufferedReader br = new BufferedReader(tastiera);
            String frase = br.readLine();

            OutputStream toSrv = sToServer.getOutputStream();
            toSrv.write(frase.getBytes(),0,frase.length());
            //Thread.sleep(120*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
