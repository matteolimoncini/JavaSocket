import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSplit {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket sToClient = null;
        try {
            serverSocket = new ServerSocket(0); //porta allocata dinamicamente
            System.out.println("Indirizzo: "+serverSocket.getInetAddress()+"; porta: "+serverSocket.getLocalPort());
            sToClient = serverSocket.accept();
            System.out.println("Indirizzo client: "+sToClient.getInetAddress()+"; porta: "+sToClient.getPort());

            int dimbuffer =100;
            byte[] buffer = new byte[dimbuffer];

            InputStream fromClient = sToClient.getInputStream();
            int letti = fromClient.read(buffer);
            if(letti>0){
                String stampa = new String(buffer,0,letti);
                System.out.println("Ricevuta stringa: "+stampa+" di "+letti+" bytes da: "+sToClient.getInetAddress()+" ; "+sToClient.getPort());

                String [] splittata = stampa.split("--");
                for (String s : splittata) System.out.println(s + " ");
            }

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
