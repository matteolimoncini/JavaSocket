import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientMoreString {
    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        try{
            InetAddress ia;                     //ip server
            InetSocketAddress isa;              //socket address server
            ia = InetAddress.getLocalHost();

            /*
            Inserimento ip e port da linea di comando
            non testato

            Scanner in= new Scanner(System.in);
            System.out.print("inserisci ip: ");

            String ip= in.next();
            ia = InetAddress.getByName(ip);

            System.out.print("inserisci il numero di porta: ");
            int numberport=in.nextInt();

            isa=new InetSocketAddress(ia,numberport);
            in.close();

            */

            isa = new InetSocketAddress(ia,52005);          //porta dest ip dest
            sToServer.connect(isa);                              //la connect richiama lei la bind
            System.out.println("porta allocata: "+sToServer.getLocalPort());

            InputStreamReader tastiera = new InputStreamReader(System.in);

            BufferedReader br = new BufferedReader(tastiera);

            while (true) {
                System.out.print("inserisci testo:");
                String frase = br.readLine();
                System.out.println("messaggio: " + frase);

                OutputStream toSrv = sToServer.getOutputStream();
                toSrv.write(frase.getBytes(), 0, frase.length());

                if (frase.equals("0")) {
                    System.out.println("chiusura connessione in corso...");
                    sToServer.close();
                    System.out.println("socket con il server chiusa");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
