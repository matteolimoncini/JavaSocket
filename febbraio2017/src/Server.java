import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.StringTokenizer;

public class Server {
    private static final int DIM_BUFFER = 100;
    private static final String INPUT_STAZIONE_OK = "INPUT STAZIONE OK";
    private static final Treno[] listatreni = new Treno[9];
    private static final String ERROR_USER = "ERROR USER";

    public static void main(String[] args) {
        String messageFromClient;

        for (int i = 0; i < listatreni.length; i++) {
            listatreni[i] = new Treno(i + 1);
        }

        DatagramSocket sToClient = null;
        DatagramPacket dpin;
        SocketAddress isaClient = null;
        byte[] buffer;
        try {
            sToClient = new DatagramSocket(0);
            System.out.println("SERVER: indirizzo: " + sToClient.getLocalAddress() + " porta: " + sToClient.getLocalPort());
            while (true) {
                try {

                    buffer = new byte[DIM_BUFFER];
                    dpin = new DatagramPacket(buffer, DIM_BUFFER);
                    sToClient.receive(dpin);
                    System.out.println("CLIENT: indirizzo: " + dpin.getAddress().getHostAddress() + " porta: " + dpin.getPort());
                    isaClient = dpin.getSocketAddress();

                    messageFromClient = new String(buffer, 0, dpin.getLength());
                    char identita = messageFromClient.charAt(0);

                    if (Character.isUpperCase(identita)) {
                        System.out.println("il client è una stazione");

                        while (true) {
                            buffer = new byte[DIM_BUFFER];
                            dpin = new DatagramPacket(buffer, DIM_BUFFER);
                            sToClient.receive(dpin);
                            messageFromClient = new String(buffer, 0, dpin.getLength());
                            if (messageFromClient.equals(".")) {
                                System.out.println("ho ricevuto dal client un segnale di end");
                                break;
                            }
                            System.out.println("ho ricevuto dal client " + messageFromClient);

                            StringTokenizer st = new StringTokenizer(messageFromClient);
                            int positionStation = -1, count = 0, number = -1;
                            String element;
                            while (st.hasMoreTokens() && count < 2) {
                                count++;
                                element = st.nextToken();
                                try {
                                    number = Integer.parseInt(element);
                                } catch (NumberFormatException e) {
                                    if (element.equals(".")) {
                                        System.err.println("stazione chiusa");
                                    } else {
                                        System.err.println("stazione: input errato");
                                        e.printStackTrace();
                                    }
                                }
                                if (count == 1) {
                                    for (int i = 0; i < listatreni.length; i++) {
                                        if (number == listatreni[i].getCodiceTreno()) {
                                            positionStation = i;
                                            listatreni[i].setIdStazioneUltimoAggiornamento(identita);
                                        }
                                    }
                                }
                                if (count == 2) {
                                    listatreni[positionStation].setRitardo(number);
                                    System.out.println("la stazione " + identita + " ha aggiornato il ritardo del treno");

                                    buffer = INPUT_STAZIONE_OK.getBytes();
                                    DatagramPacket outDp = new DatagramPacket(buffer, INPUT_STAZIONE_OK.length());
                                    outDp.setSocketAddress(isaClient);
                                    sToClient.send(outDp);
                                    System.out.println("inviato al client input stazione ok");
                                }
                            }
                            if (count > 2) throw new IOException("LA STAZIONE HA INSERITO ID E RITARDO NON VALIDO");
                        }

                    } else {
                        int num;
                        if (messageFromClient.equals(".")) {
                            System.out.println("ho ricevuto dal client un segnale di end");
                            continue;
                        }
                        try {
                            num = Integer.parseInt(messageFromClient);
                        } catch (NumberFormatException e) {
                            throw new IllegalHostException("il client non una stazione o un utente!");
                        }
                        if (num >= 1 && num <= 9) {

                            System.out.println("il client è un utente");
                            String aggiornamento = "-";

                            for (int i = 0; i < listatreni.length; i++) {
                                if (num == listatreni[i].getCodiceTreno()) {
                                    aggiornamento = listatreni[i].toString();
                                }
                            }

                            buffer = aggiornamento.getBytes();
                            dpin = new DatagramPacket(buffer, aggiornamento.length());
                            dpin.setSocketAddress(isaClient);
                            sToClient.send(dpin);
                            System.out.println("ho inviato al client un aggiornamento");

                        } else {
                            throw new IllegalHostException("il client non una stazione o un utente!");
                        }
                    }
                } catch (IllegalHostException e) {
                    System.err.println("invio allo user un errore");
                    buffer = ERROR_USER.getBytes();
                    dpin = new DatagramPacket(buffer, ERROR_USER.length());
                    dpin.setSocketAddress(isaClient);
                    try {
                        sToClient.send(dpin);
                    } catch (IOException ioException) {
                        System.err.println("errore in invio del pacchetto");
                        ioException.printStackTrace();
                    }
                    System.err.println("ho inviato allo user un errore");
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert sToClient != null;
            sToClient.close();
            System.err.println("chiusura socket");
        }

    }

    private static class IllegalHostException extends Throwable {
        public IllegalHostException(String s) {
            System.err.println(s);
        }
    }
}
