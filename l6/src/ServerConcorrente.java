import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import static java.net.SocketOptions.SO_TIMEOUT;

public class ServerConcorrente {
    public static void main(String[] args) {
        int max_conn=10;
        int i = 0;
        int index = 0;
        int dimbuffer=100;
        byte[] buffer = new byte[dimbuffer];
        int soTimeout=1000*60;
        ArrayList<Socket> sClient = new ArrayList<Socket>(max_conn);
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            System.out.println("SERVER: address:"+serverSocket.getInetAddress()+" port: "+serverSocket.getLocalPort());
            while (true){
                try {
                    serverSocket.setSoTimeout(soTimeout);
                    while (index<max_conn){
                        sClient.add(serverSocket.accept());
                        index++;
                    }
                } catch (SocketTimeoutException e) {
                    System.err.println("Timeout exception catched");
                } catch (IOException e) {
                    System.err.println("IoException catched");
                    e.printStackTrace();
                }


                while (index>0){
                    System.out.println("CLIENT: address:"+sClient.get(i).getInetAddress()+" port: "+sClient.get(i).getLocalPort());

                    try {
                        sClient.get(i).setSoTimeout(soTimeout);
                        InputStream inputStream = sClient.get(i).getInputStream();

                        while (true){
                            int letti = inputStream.read(buffer);
                            String message = (new String(buffer,0,letti)).trim();

                            if(message.equals("0")){
                                throw new EndOfClientException ("End of Client");
                            }
                            System.out.println("MESSAGGIO: "+message);
                        }

                    } catch (SocketTimeoutException e) {
                        System.out.println("CLIENT: Timeout expired!");
                        e.printStackTrace();
                    } catch (EndOfClientException e) {
                        try {
                            sClient.get(i).close();
                            sClient.remove(i);
                            index--;
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
                if(index!=0)
                    i=(i+1)%index;
                else i=0;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class EndOfClientException extends Throwable {
        EndOfClientException(String end_of_client) {
        }
    }
}
