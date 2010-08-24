
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CIAdmin
 */
public class TestEnergyReader {
    public static void  main (String [] a){
        new TestEnergyReader();
    }
    public TestEnergyReader() {
        FileInputStream fis = null;
        String state;
        double value;
        long simTime;
        long realTime;
        String event;
        short id;
        try {
            fis = new FileInputStream("CSV1274572042542.csv");

            DataInputStream dis = new DataInputStream(new BufferedInputStream(fis));
            while (dis.available() > 0) {
                id = dis.readShort();
                event = dis.readUTF();
                realTime = dis.readLong();
                simTime = dis.readLong();
                value = dis.readDouble();
                state = dis.readUTF();
                System.out.println(id+";"+event+";"+realTime+";"+simTime+";"+value+";"+state);
            }
        } catch (IOException ex) {
            Logger.getLogger(TestEnergyReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(TestEnergyReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
