import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 {
    public static void main(String[] args) {
        Socket sToServer;
        sToServer = new Socket();
        int dimbuffer =100;
        byte[] buffer = new byte[dimbuffer];
        String notification="ACCETTATO";
        String recived="";
        try {
            InetAddress inetAddress;
            InetSocketAddress inetSocketAddress;
            inetAddress = InetAddress.getLocalHost();
            inetSocketAddress = new InetSocketAddress(inetAddress,51156);
            sToServer.connect(inetSocketAddress);
            System.out.println("CLIENT: porta:"+sToServer.getLocalPort());

            InputStream fromSrv = sToServer.getInputStream();
            int letti = fromSrv.read(buffer);

            if(letti>0){
                recived=new String(buffer,0,letti);
                if(notification.equals(recived)){
                    System.out.println("ho ricevuto ok dal server");
                }
            }

            System.out.println("inserisci testo");
            InputStreamReader tastiera = new InputStreamReader(System.in);

            BufferedReader bufferedReader = new BufferedReader(tastiera);
            String frase = bufferedReader.readLine();

            OutputStream outputStream = sToServer.getOutputStream();
            outputStream.write(frase.getBytes(),0,frase.length());

            //Thread.sleep(1000*10);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
