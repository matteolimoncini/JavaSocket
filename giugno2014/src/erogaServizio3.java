import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class erogaServizio3 extends Thread {
    private static final String FEEDBACK_OK = "FEEDBACK OK";
    private static final String FEEDBACK_ERROR = "FEEDBACK ERRATO";
    private Socket socket;

    public erogaServizio3(Socket toClient) {
        this.socket = toClient;
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[Server.DIM_BUFFER];
            InputStream fromCl = socket.getInputStream();
            OutputStream toCl = socket.getOutputStream();
            int letti;
            String msgfromCl;


            StringBuilder sb = new StringBuilder();

            forecastItem[] previsioni = Server.previsioni;
            for (int i = 0; i < previsioni.length; i++) {
                sb.append(previsioni[i].toString()).append("\n");
            }
            String previsione = sb.toString();
            toCl.write(previsione.getBytes(), 0, previsione.length());
            System.out.println("ho inviato al client le previsioni");

            int trefeedback = 0;
            boolean isNotCorrect = false;
            String insert = "inserisci id feedback";
            while (trefeedback < 3) {
                isNotCorrect = false;
                //toCl.write(insert.getBytes(),0,insert.length());
                //System.out.println("INVIATO AL CLIENT inserisci id feedback");
                try {
                    letti = fromCl.read(buffer);
                } catch (IOException e) {
                    throw new ReadException();
                }
                if (letti > 0) {
                    msgfromCl = new String(buffer, 0, letti);

                    System.out.println("RICEVUTA STRINGA: " + msgfromCl + " di " + letti + " byte da " + socket.getInetAddress() + " ; " + socket.getPort());
                    String echo = "HO RICEVUTO STRINGA";
                    //toCl.write(echo.getBytes(), 0, echo.length());
                    //System.out.println("inviato echo al client");

                    Scanner in = new Scanner(msgfromCl);
                    String string;
                    char id = '-';
                    int feedback = 0;
                    for (int i = 0; i < 2; i++) {
                        try {
                            string = in.next();
                        } catch (NoSuchElementException e) {
                            System.err.println("scanner error!");
                            isNotCorrect = true;
                            break;
                        }
                        if (i == 0) {
                            if (!Arrays.asList(Server.ID_AMMESSI).contains(string)) {
                                isNotCorrect = true;
                            }
                            id = string.charAt(0);
                        }
                        if (i == 1) {
                            try {
                                int integer = Integer.parseInt(string);
                                if (integer > 1 || integer < -1) {
                                    isNotCorrect = true;
                                }
                                feedback = integer;
                            } catch (NumberFormatException e) {
                                isNotCorrect = true;
                            }
                        }
                    }
                    if (!isNotCorrect) {
                        System.out.println("hai inserito un feedback corretto");
                        toCl.write(FEEDBACK_OK.getBytes(), 0, FEEDBACK_OK.length());
                        System.out.println("ho inviato al client feedback ok");


                        trefeedback++;
                        isNotCorrect = false;
                        char idremoto;
                        int oldfeedback;

                        for (int i = 0; i < Server.previsioni.length; i++) {
                            idremoto = Server.previsioni[i].getId();
                            if (id == idremoto) {
                                oldfeedback = Server.previsioni[i].getFeedback();
                                Server.previsioni[i].setFeedback(oldfeedback + feedback);
                                break;
                            }
                        }
                        //sort qui
                        Collections.sort(Arrays.asList(Server.previsioni));

                        System.out.println("ho settato il feedback correttamente");

                    } else {
                        System.out.println("hai inserito un feedback errato");
                        toCl.write(FEEDBACK_ERROR.getBytes(), 0, FEEDBACK_ERROR.length());
                        System.out.println("ho inviato al client feedback error");
                    }

                } else {
                    throw new ReadException();
                }

            }


        } catch (ReadException e) {
            System.err.println("errore nella read");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.err.println("socket chiusa indirizzo:" + socket.getInetAddress() + " porta: " + socket.getPort());
            } catch (IOException e) {
                System.err.println("can not close the socket indirizzo:" + socket.getInetAddress() + " porta: " + socket.getPort());
                e.printStackTrace();
            }
        }

    }

    private class ReadException extends Throwable {
    }
}
