import java.io.IOException;
import java.net.*;
import java.util.StringTokenizer;

public class Server {
    private static final int DIM_BUFFER = 100;
    private static final String INPUT_STAZIONE_OK = "INPUT STAZIONE OK";
    private static Treno[] listatreni = new Treno[9];

    public static void main(String[] args) {
        String messageFromClient;

        for (int i = 0; i < listatreni.length; i++) {
            listatreni[i] = new Treno(i + 1);
        }

        DatagramSocket sToClient = null;
        try {
            byte[] buffer;
            DatagramPacket dpin;
            sToClient = new DatagramSocket(0);
            System.out.println("SERVER: indirizzo: " + sToClient.getLocalAddress() + " porta: " + sToClient.getLocalPort());
            while (true) {
                buffer = new byte[DIM_BUFFER];
                dpin = new DatagramPacket(buffer, DIM_BUFFER);
                sToClient.receive(dpin);
                System.out.println("CLIENT: indirizzo: " + dpin.getAddress().getHostAddress() + " porta: " + dpin.getPort());
                SocketAddress isaClient = dpin.getSocketAddress();

                messageFromClient = new String(buffer, 0, dpin.getLength());
                char identita = messageFromClient.charAt(0);

                if (Character.isUpperCase(identita)) {
                    System.out.println("il client è una stazione");

                    while (true) {
                        buffer = new byte[DIM_BUFFER];
                        dpin = new DatagramPacket(buffer, DIM_BUFFER);
                        sToClient.receive(dpin);
                        messageFromClient = new String(buffer, 0, dpin.getLength());
                        if(messageFromClient.equals(".")){
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

                } else if (Character.isLowerCase(identita)) {

                    System.out.println("il client è un utente");
                    buffer = new byte[DIM_BUFFER];
                    dpin = new DatagramPacket(buffer, DIM_BUFFER);


                } else {
                    throw new IllegalHostException("il client non una stazione o un utente!");
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IllegalHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            assert sToClient!=null;
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
