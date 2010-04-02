
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author posilva
 */
public class BouncyCastle {

    private static ByteArrayOutputStream bOut;
    private static CipherOutputStream cOut;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InvalidKeyException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        try {
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            Key key = kg.generateKey();
            Cipher cipher = Cipher.getInstance("DES");
            byte[] data = "Hello World!".getBytes();
            System.out.println("Original data : " + new String(data));
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(data);

            System.out.println("Encrypted data: " + new String(result));
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] original = cipher.doFinal(result);
            System.out.println("Decrypted data: " + new String(original));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }
}
