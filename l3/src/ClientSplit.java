import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSplit {
    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        try{
            InetAddress ia;                     //ip server
            InetSocketAddress isa;              //socket address server
            ia = InetAddress.getLocalHost();
            isa = new InetSocketAddress(ia,54250);          //porta dest ip dest
            sToServer.connect(isa);                              //la connect richiama lei la bind
            System.out.println("porta allocata: "+sToServer.getLocalPort());

            InputStreamReader tastiera = new InputStreamReader(System.in);

            BufferedReader br = new BufferedReader(tastiera);

            System.out.print("inserisci testo:");
            String frase = br.readLine();
            System.out.print("inserisci float:");
            double numero = Double.parseDouble(br.readLine());
            String totale = frase+ "--"+Double.toString(numero);
            System.out.println("messaggio: "+totale);

            OutputStream toSrv = sToServer.getOutputStream();
            toSrv.write(totale.getBytes(),0,totale.length());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
