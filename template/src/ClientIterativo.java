import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientIterativo {
    public static void main(String[] args) {
        Socket sToServer = new Socket();

        try {

            InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 1234);
            sToServer.connect(isa);
            OutputStream toServer = sToServer.getOutputStream();
            InputStreamReader tastiera = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(tastiera);
            String input = br.readLine();

            toServer.write(input.getBytes());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sToServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
