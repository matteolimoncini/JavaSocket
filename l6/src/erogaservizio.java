import javax.swing.plaf.TableHeaderUI;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class erogaservizio extends Thread {
    private Socket socket;
    public erogaservizio(Socket toClient) {
        this.socket = toClient;
    }

    @Override
    public void run() {
        int dimbuffer=100;
        byte[] buffer = new byte[dimbuffer];

        while (true){
            try {
                InputStream fromClient = socket.getInputStream();
                int letti = fromClient.read(buffer);
                if(letti>0){
                    String stampa = new String(buffer,0,letti);
                    System.out.println("RICEVUTA STRINGA: "+stampa+ " di "+letti+ " byte da "+socket.getInetAddress()+"; porta: "+socket.getPort());
                }else{
                    System.out.println("CLIENT: "+socket.getInetAddress()+" CHIUSO");
                    socket.close();
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
