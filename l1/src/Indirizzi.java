import java.net.InetAddress;
import java.net.UnknownHostException;

public class Indirizzi {
    public static void main(String[] args) {
        String nome = "www.unimi.it";

        try {
            InetAddress ia = InetAddress.getByName(nome);
            byte[] ndp = ia.getAddress();
            System.out.println("Indirizzo: " + (ndp[0] & 0xff) + "." + (ndp[1] & 0xff) + "." + (ndp[2] & 0xff) + "." + (ndp[3] & 0xff));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //another method
        try {
            InetAddress[] iaa = InetAddress.getAllByName(nome);
            for (int i = 0; i < iaa.length; i++) {
                System.out.println("Indirizzo: " + iaa[i].getHostName() + " --> " + iaa[i].getHostAddress());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //my ip
        try {
            InetAddress iaa = InetAddress.getLocalHost();
            System.out.println("Indirizzo: " + iaa.getHostName() + " --> " + iaa.getHostAddress());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}