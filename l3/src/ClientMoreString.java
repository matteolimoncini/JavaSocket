import java.io.*;
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
            int dimbuffer = 100;
            byte[] buffer = new byte[dimbuffer];
            // isa = new InetSocketAddress(ia,52005);          //porta dest ip dest

            Scanner in= new Scanner(System.in);
            //System.out.print("inserisci ip: ");
            //String ip= in.next();
            ia = InetAddress.getLocalHost();
            //ia = InetAddress.getByName(ip);
            System.out.print("inserisci il numero di porta: ");
            int numberport=in.nextInt();
            isa=new InetSocketAddress(ia,numberport);

            sToServer.connect(isa);                              //la connect richiama lei la bind
            System.out.println("porta allocata: "+sToServer.getLocalPort());

            InputStreamReader tastiera = new InputStreamReader(System.in);

            BufferedReader br = new BufferedReader(tastiera);

            boolean continuable=true;
            while (continuable) {
                System.out.print("inserisci testo:");
                String frase = br.readLine();
                System.out.println("messaggio: " + frase);

                OutputStream toSrv = sToServer.getOutputStream();
                toSrv.write(frase.getBytes(), 0, frase.length());

                if (frase.equals("0")) {
                    continuable=false;
                }

                String notifyMessagge;
                notifyMessagge = "enable";


                InputStream fromSrv=sToServer.getInputStream();
                int letti =fromSrv.read(buffer);
                String srvNotification = new String(buffer,0,letti);
                System.out.println(srvNotification);
                if(!(srvNotification.equals(notifyMessagge))) continuable=false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                sToServer.close();
            }catch (Exception e){
                System.err.println("Client error");
                e.printStackTrace();
            }
        }
    }
}
