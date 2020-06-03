import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class ErogaServizio extends Thread {
    private static final String OK_INPUT = "OK INPUT";
    private static final String ERROR_INPUT = "ERROR INPUT";
    private final Socket sToClient;

    public ErogaServizio(Socket sToClient) {
        this.sToClient = sToClient;
    }

    @Override
    public void run() {
        try {
            System.out.println("CLIENT: indirizzo:"+sToClient.getInetAddress()+" porta:"+sToClient.getPort());
            byte[] buffer = new byte[Server.DIM_BUFFER];
            int letti;
            String messageFromClient;
            InputStream fromCl = this.sToClient.getInputStream();
            OutputStream toCl = this.sToClient.getOutputStream();
            toCl.write(Server.distrPrezzo.toString().getBytes());
            System.out.println("inviato al client aggiornamento distr prezzo");

            while (true) {
                System.out.println("aspetto i dati dal client...");
                try {
                    letti = fromCl.read();
                } catch (IOException e) {
                    throw new IOException("READ ERROR!");
                }
                if (letti <= 0) {
                    throw new IOException("READ ERROR <0 byte letti; la socket potrebbe essersi chiusa inaspettatamente");
                }
                messageFromClient = new String(buffer, 0, letti);
                System.out.println("RICEVUTO DAL CLIENT: " + messageFromClient);
                String prezzo, dist;
                if (messageFromClient.matches("[1-7]\\s[0-9]{1,4}")) {
                    StringTokenizer st = new StringTokenizer(messageFromClient);
                    dist = st.nextToken();
                    prezzo = st.nextToken();
                    if (Integer.parseInt(prezzo) <= Server.distrPrezzo.getPrezzo()) {
                        Server.distrPrezzo.setDistributore(Integer.parseInt(dist));
                        Server.distrPrezzo.setPrezzo(Integer.parseInt(prezzo));
                        System.out.println("distributore e prezzo settati correttamente");
                    }
                    toCl.write(OK_INPUT.getBytes());
                } else {
                    System.err.println("input errato");
                    toCl.write(ERROR_INPUT.getBytes());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                sToClient.close();
                System.out.println("socket chiusa");
            } catch (IOException e) {
                System.err.println("can not close the socket!");
                e.printStackTrace();
            }
        }


    }
}
