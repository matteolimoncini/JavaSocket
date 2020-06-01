import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerCalcolatrice {
    private static final int DIM_BUFFER = 100;
    private static final String END_CLIENT = ".";
    private static  final String errorOperatore = "OPERATORE NON CORRETTO. REINSERIRE OPERATORE";
    private static  final String vald = "VALIDO";
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket toClient;
        OutputStream toCl;
        InputStream fromCl;
        int letti;
        byte[] buffer;
        final String[] ammessi = {"+", "-", "*", "/"};
        String operatore = "";
        boolean numberFormatException = false;
        boolean isOperatoreCorretto;
        String operando1 = "";
        boolean isOperandoCorretto;
        double op1 = 0, op2 = 0;

        try {
            serverSocket = new ServerSocket(0,3);
            System.out.println("SERVER: indirizzo:" + serverSocket.getInetAddress() + " porta:" + serverSocket.getLocalPort());

            while (true) {
                toClient = serverSocket.accept();
                System.out.println("CLIENT: indirizzo: " + toClient.getInetAddress() + " porta: " + toClient.getPort());

                try {

                    while (true) { //un client
                        toCl = toClient.getOutputStream();
                        fromCl = toClient.getInputStream();
                        buffer = new byte[DIM_BUFFER];

                        do {
                            buffer = new byte[DIM_BUFFER];
                            letti = fromCl.read(buffer);
                            if (letti > 0) {
                                operatore = new String(buffer, 0, letti);

                                if (operatore.equals(END_CLIENT)){
                                    System.out.println("ricevuto end client");
                                    throw new EndClientException();
                                }

                                isOperatoreCorretto = Arrays.asList(ammessi).contains(operatore);
                            } else {
                                isOperatoreCorretto = false;
                            }
                            if (!isOperatoreCorretto) {
                                System.out.println("Operatore non corretto. Carattere non consentito");
                                toCl.write(errorOperatore.getBytes(), 0, errorOperatore.length());
                            }
                        } while (!isOperatoreCorretto);
                        System.out.println(operatore);
                        toCl.write(vald.getBytes(),0,vald.length());
                        System.out.println("inviato ack al client");

                        do {
                            buffer = new byte[DIM_BUFFER];
                            letti = fromCl.read(buffer);
                            if (letti > 0) {
                                operando1 = new String(buffer, 0, letti);

                                if (operando1.equals(END_CLIENT)) {
                                    System.out.println("ricevuto end client");
                                    throw new EndClientException();
                                }

                                try {
                                    op1 = Double.parseDouble(operando1);
                                    isOperandoCorretto = true;
                                } catch (NumberFormatException notDouble) {
                                    System.out.println("inserito operando non corretto");
                                    isOperandoCorretto = false;
                                }
                            } else {
                                isOperandoCorretto = false;
                            }
                            if (!isOperandoCorretto) {
                                System.out.println("Operando non corretto. Carattere non consentito");
                                String errorOperando = "OPERANDO NON CORRETTO. REINSERIRE OPERANDO";
                                toCl.write(errorOperando.getBytes());
                            }
                        } while (!isOperandoCorretto);
                        System.out.println(operando1);
                        toCl.write(vald.getBytes(),0,vald.length());
                        System.out.println("inviato ack al client");
                        isOperandoCorretto=true;

                        do {
                            buffer = new byte[DIM_BUFFER];
                            letti = fromCl.read(buffer);
                            if (letti > 0) {
                                operando1 = new String(buffer, 0, letti);
                                if (operando1.equals(END_CLIENT)) {
                                    System.out.println("ricevuto end client");
                                    throw new EndClientException();
                                }
                                try {
                                    op2 = Double.parseDouble(operando1);
                                    isOperandoCorretto = true;
                                } catch (NumberFormatException notDouble) {
                                    System.out.println("inserito operando non corretto");
                                    isOperandoCorretto = false;
                                }
                            } else {
                                isOperandoCorretto = false;
                            }
                            if (!isOperandoCorretto) {
                                System.out.println("Operando non corretto. Carattere non consentito");
                                String errorOperando = "OPERANDO NON CORRETTO. REINSERIRE OPERANDO";
                                toCl.write(errorOperando.getBytes());
                            }
                        } while (!isOperandoCorretto);
                        System.out.println(operando1);
                        toCl.write(vald.getBytes(),0,vald.length());
                        System.out.println("inviato ack al client");
                        isOperandoCorretto=true;

                        double risultato = 0;
                        switch (operatore) {
                            case "+":
                                risultato = op1 + op2;
                                break;
                            case "-":
                                risultato = op1 - op2;
                                break;
                            case "*":
                                risultato = op1 * op2;
                                break;
                            case "/":
                                risultato = op1 / op2;
                                break;
                        }
                        System.out.println("risultato: " + risultato);
                        toCl.write(Double.toString(risultato).getBytes(), 0, Double.toString(risultato).length());

                    }
                } catch (EndClientException endClient) {
                    try {
                        toClient.close();
                        System.err.println("socket chiusa");
                    }catch (IOException e){
                        System.err.println("can not close the socket");
                        e.printStackTrace();
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class EndClientException extends Throwable {
    }
}