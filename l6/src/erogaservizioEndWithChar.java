import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class erogaservizioEndWithChar extends Thread {
    private static final String END_STRING = ".";
    private Socket socket;
    erogaservizioEndWithChar(Socket toClient) {
        this.socket = toClient;
    }

    @Override
    public void run() {
        int dimbuffer=100;
        byte[] buffer = new byte[dimbuffer];
        int letti;
        InputStream fromClient;

        while (true){
            try {
                fromClient = socket.getInputStream();
                letti = fromClient.read(buffer);
                if(letti>0){
                    String stampa = new String(buffer,0,letti);
                    if(stampa.equals(END_STRING)){
                        socket.close();
                        System.out.println("CLIENT: "+socket.getInetAddress()+" CHIUSO");
                        return;
                    }
                    System.out.println("RICEVUTA STRINGA: "+stampa+ " di "+letti+ " byte da "+socket.getInetAddress()+"; porta: "+socket.getPort());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
