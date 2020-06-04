import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class utente {
    private static final int DIM_BUFFER = 20;
    private static final String ACK = "ACK";
    private static final int NUMPORTASERVER=37572;

    public static void main(String[] args) {
        InetSocketAddress isaServer;
        DatagramSocket sToServer = null;
        DatagramPacket dpin;
        DatagramPacket dpout;
        InputStreamReader tastiera;
        BufferedReader br;
        byte[] buffer;
        String messageFromServer;
        String messageToServer;

        try {
            isaServer = new InetSocketAddress(InetAddress.getLocalHost(), NUMPORTASERVER);
            sToServer = new DatagramSocket();
            System.out.println("CLIENT: indirizzo: " + sToServer.getLocalAddress() + " porta: " + sToServer.getLocalPort());

            tastiera = new InputStreamReader(System.in);
            br = new BufferedReader(tastiera);
            boolean exit = false;

            while (!exit) {//registrazione
                buffer = new byte[DIM_BUFFER];

                System.out.println("inserisci nome da utilizzare per la registrazione: R [id]");
                messageToServer = br.readLine();

                dpout = new DatagramPacket(messageToServer.getBytes(), 0, messageToServer.length());
                dpout.setSocketAddress(isaServer);
                sToServer.send(dpout);  //inviato al server registrazione

                dpin = new DatagramPacket(buffer, DIM_BUFFER);
                sToServer.receive(dpin);
                messageFromServer = new String(buffer, 0, dpin.getLength());
                System.out.println(messageFromServer);

                exit = true;
                if (!messageFromServer.equals(ACK)) {
                    exit = false;
                    System.out.println("ERRORE! il server ha inviato un errore");
                }
            }

            while (true) {

                System.out.println("se vuoi modificare inserisci: M [idregistazione]");
                messageToServer = br.readLine();
                dpout = new DatagramPacket(messageToServer.getBytes(), 0, messageToServer.length());
                dpout.setSocketAddress(isaServer);
                sToServer.send(dpout); //inviato al server il messaggio di modifica

                buffer = new byte[DIM_BUFFER];
                dpin = new DatagramPacket(buffer, DIM_BUFFER);
                sToServer.receive(dpin);
                messageFromServer = new String(buffer, 0, dpin.getLength());
                System.out.println("FROM SERVER:"+messageFromServer); //ricevuto messaggio dal server

                System.out.println("inserisci il contenuto modificato: - [id] string");
                messageToServer = br.readLine();
                dpout = new DatagramPacket(messageToServer.getBytes(), 0, messageToServer.length());
                dpout.setSocketAddress(isaServer);
                sToServer.send(dpout);  //inviato al server il contenuto modificato

                buffer = new byte[DIM_BUFFER];
                dpin = new DatagramPacket(buffer, DIM_BUFFER);
                sToServer.receive(dpin);
                messageFromServer = new String(buffer, 0, dpin.getLength());
                System.out.println("FROM SERVER:"+messageFromServer); //ricevuto messaggio dal server
            }

        }
        catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert sToServer != null;
            sToServer.close();
        }
    }
}

