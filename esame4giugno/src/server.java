import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class server {
    private static final int DIM_BUFFER = 20;
    private static final String ACK = "ACK";
    private static final String NACK = "NACK";
    private static final String NIL = "NIL";
    private static final String UPDATE_CONTENUTO = "UP";
    private static ArrayList<String> listaUtenti = new ArrayList<>();
    private static coppieUtenti[] listaCoppieUtenti = new coppieUtenti[3];

    public static void main(String[] args) {
        listaCoppieUtenti[0] = new coppieUtenti("A", "B");
        listaCoppieUtenti[1] = new coppieUtenti("C", "D");
        listaCoppieUtenti[2] = new coppieUtenti("E", "F");

        DatagramPacket dpin, dpout;
        DatagramSocket sToClient = null;
        byte[] buffer;
        try {
            sToClient = new DatagramSocket();
            System.out.println("SERVER: indirizzo:" + sToClient.getLocalAddress() + "porta: " + sToClient.getLocalPort());
            String messageFromClient;
            SocketAddress clientAdd;

            while (true) {
                buffer = new byte[DIM_BUFFER];
                dpin = new DatagramPacket(buffer, DIM_BUFFER);
                sToClient.receive(dpin);
                System.out.println("CLIENT: indirizzo:" + dpin.getAddress() + "porta: " + dpin.getPort());
                messageFromClient = new String(buffer, 0, dpin.getLength());
                System.out.println("FROM CLIENT:"+messageFromClient);
                clientAdd = dpin.getSocketAddress();

                if (messageFromClient.matches("R [A-F]")) {//messaggio di registrazione
                    messageFromClient = messageFromClient.substring(2);

                    if (!listaUtenti.contains(messageFromClient)) {

                        listaUtenti.add(messageFromClient);
                        //TODO dove mi salvo indirizzo e porta del client?
                        dpout = new DatagramPacket(ACK.getBytes(), 0, ACK.length());
                        dpout.setSocketAddress(clientAdd);
                        sToClient.send(dpout);

                    } else {//SEND NACK
                        dpout = new DatagramPacket(NACK.getBytes(), 0, NACK.length());
                        dpout.setSocketAddress(clientAdd);
                        sToClient.send(dpout);
                    }

                }
                if (messageFromClient.matches("M [A-F]")) {//messaggio di modifica
                    messageFromClient = messageFromClient.substring(2);

                    int numcoppia;
                    numcoppia = getNumcoppia(messageFromClient);

                    if (!listaCoppieUtenti[numcoppia].getContenuto().equals("-")) {
                        String contenuto = listaCoppieUtenti[numcoppia].getContenuto();
                        String lastMod = listaCoppieUtenti[numcoppia].getLastModify();
                        String invio = contenuto + lastMod;
                        dpout = new DatagramPacket(invio.getBytes(), 0, invio.length());
                        dpout.setSocketAddress(clientAdd);
                        sToClient.send(dpout);
                    } else {//invia NIL
                        dpout = new DatagramPacket(NIL.getBytes(), 0, NIL.length());
                        dpout.setSocketAddress(clientAdd);
                        sToClient.send(dpout);
                    }
                    listaCoppieUtenti[numcoppia].setLock();


                    buffer = new byte[DIM_BUFFER];
                    dpin = new DatagramPacket(buffer, DIM_BUFFER);
                    sToClient.receive(dpin);
                    messageFromClient = new String(buffer, 0, dpin.getLength());
                    System.out.println("FROM CLIENT:"+messageFromClient);

                    if (messageFromClient.matches("- [A-F] \\w+")) {//modifico qualcosa
                        String user = messageFromClient.substring(2,3);
                        numcoppia = getNumcoppia(user);
                        String newContenuto = messageFromClient.substring(3).trim();
                        listaCoppieUtenti[numcoppia].setContenuto(newContenuto,user);

                        dpout = new DatagramPacket(UPDATE_CONTENUTO.getBytes(), 0, UPDATE_CONTENUTO.length());
                        dpout.setSocketAddress(clientAdd);
                        sToClient.send(dpout);  //inviata conferma al client

                        listaCoppieUtenti[numcoppia].setUnlock();
                    }
                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert sToClient != null;
            sToClient.close();
        }
    }

    private static int getNumcoppia(String messageFromClient) {
        int numcoppia = -1;
        switch (messageFromClient) {
            case "A":
            case "B":
                numcoppia = 0;
                break;
            case "C":
            case "D":
                numcoppia = 1;
                break;
            case "E":
            case "F":
                numcoppia = 2;
                break;
        }
        return numcoppia;
    }

    private static class coppieUtenti {
        private final String user1;
        private final String user2;
        private String contenuto;
        private String lastModify;
        private boolean lock = false;

        public coppieUtenti(String e, String f) {
            this.user1 = e;
            this.user2 = f;
            this.contenuto= new String("-");
        }

        public String getContenuto() {
            return contenuto;
        }

        public void setContenuto(String contenuto, String user) {
            this.contenuto = contenuto;
            this.lastModify = user;
        }

        public String getLastModify() {
            return lastModify;
        }

        public void setLock() {
            this.lock = true;
        }

        public void setUnlock() {
            this.lock = false;
        }
    }
}
