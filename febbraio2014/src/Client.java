import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static final String OK_INPUT = "OK INPUT";
    private static final String ERROR_INPUT = "ERROR INPUT";
    protected static final int DIM_BUFFER=100;

    public static void main(String[] args) {
        Socket sToServer = new Socket();
        InputStream fromServer;
        OutputStream toServer;
        try {
            byte[] buffer;
            InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(),55555);
            sToServer.connect(isa);
            System.out.println("CLIENT: indirizzo:"+sToServer.getLocalAddress()+" porta:"+sToServer.getLocalPort());

            fromServer = sToServer.getInputStream();
            toServer = sToServer.getOutputStream();
            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(tastiera);
            String messageFromServer;
            String messageToServer;
            buffer = new byte[DIM_BUFFER];
            int letti;
            try {
                letti = fromServer.read(buffer);
            }catch (IOException e){
                throw new IOException("READ ERROR!");
            }
            if(letti<=0){
                throw new IOException("READ ERROR BYTE LETTI<=0 !");
            }
            messageFromServer= new String(buffer,0,letti);
            System.out.println("RICEVUTO FROM SERVER:"+messageFromServer);

            while (true){
                buffer = new byte[DIM_BUFFER];
                System.out.println("inserisci distributore prezzo");
                messageToServer = br.readLine();
                if(messageToServer.equals(".")) {
                    System.out.println("ricevuto segnale di end");
                    break;
                }
                toServer.write(messageToServer.getBytes(),0,messageToServer.length());
                System.out.println("inviato al server");

                try {
                    letti = fromServer.read(buffer);
                }catch (IOException e){
                    throw new IOException("READ ERROR!");
                }

                if(letti<=0){
                    throw new IOException("READ ERROR BYTE LETTI<=0 !");
                }

                messageFromServer= new String(buffer,0,letti);
                if(messageFromServer.equals(OK_INPUT)){
                    System.out.println("input corretto");
                }
                if (messageFromServer.equals(ERROR_INPUT)){
                    System.out.println("input errato");
                }

            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                sToServer.close();
                System.out.println("socket chiusa");
            } catch (IOException e) {
                System.err.println("can not close the socket");
                e.printStackTrace();
            }
        }
    }
}
