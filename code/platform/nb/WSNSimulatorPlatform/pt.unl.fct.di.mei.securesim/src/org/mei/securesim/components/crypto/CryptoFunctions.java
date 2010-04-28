package org.mei.securesim.components.crypto;

/**
 *
 * @author posilva
 */
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.engines.SkipjackEngine;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.params.KeyParameter;

public class CryptoFunctions {

//    private static final String CYPHER_SUITE = "Skipjack/CTR/NoPadding";
    private static final String CYPHER_SUITE = "Skipjack/CFB/NoPadding";
    private static final String CYPHER_ALGORITHM = "Skipjack";
    public static final int BLOCK_SIZE = 8, MIC_SIZE = 4, KEY_SIZE = 10, MAC_SIZE = 8;
    private static byte[] keyData;
    private static byte[] ivData;

    public static byte[] cipherData(byte[] data, byte[] key, byte[] iv) {
        try {

            SecretKeySpec key_spec = new SecretKeySpec(key, CYPHER_ALGORITHM);
            Cipher c = Cipher.getInstance(CYPHER_SUITE, "BC");
            c.init(Cipher.ENCRYPT_MODE, key_spec, new IvParameterSpec(iv));
            return c.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] decipherData(byte[] data, byte[] key, byte[] iv) {
        try {
            SecretKeySpec key_spec = new SecretKeySpec(key, CYPHER_ALGORITHM);
            Cipher c = Cipher.getInstance(CYPHER_SUITE, "BC");
            c.init(Cipher.DECRYPT_MODE, key_spec, new IvParameterSpec(iv));
            return c.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] createMIC(byte[] data, byte[] key) {
        CMac mic = new CMac(new SkipjackEngine(), MIC_SIZE * 8);
        byte[] buffer = new byte[MIC_SIZE];
        mic.init(new KeyParameter(key));
        mic.update(data, 0, data.length);
        mic.doFinal(buffer, 0);
        return buffer;
    }

    public static byte[] createMAC(byte[] data, byte[] key) {
        CMac mic = new CMac(new SkipjackEngine(), MAC_SIZE * 8);
        byte[] buffer = new byte[MAC_SIZE];
        mic.init(new KeyParameter(key));
        mic.update(data, 0, data.length);
        mic.doFinal(buffer, 0);
        return buffer;
    }

    public static byte[] createIV(int counter) {
        byte[] iv = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE / (Integer.SIZE / 8); i++) {
            for (int j = 0; j < Integer.SIZE / 8; j++) {
                iv[j + (i * (Integer.SIZE / 8))] = (byte) (counter & 0xFF);
                counter = counter >> 8;
            }
        }
        return iv;
    }

    public static byte[] createSkipjackKey() {
        try {
            return createSkipjackKeyObject().getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.exit(0);
        return null;
    }

    public static boolean verifyMessageIntegrity(byte[] payload, byte[] received_mic, byte[] key) {
        return Arrays.equals(received_mic, CryptoFunctions.createMIC(payload, key));
    }

    public static byte[] createNextKey(byte[] lastKey) {
        CMac mic = new CMac(new SkipjackEngine(), BLOCK_SIZE * 8);
        byte[] buffer = new byte[BLOCK_SIZE];
        mic.init(new KeyParameter(lastKey));
        mic.update(lastKey, 0, lastKey.length);
        mic.doFinal(buffer, 0);
        return Arrays.copyOf(buffer, KEY_SIZE);

    }

    public static byte[][] createOneWayKeyChain(int size) {
        byte[][] keyChain = new byte[size][];
        byte[] lastKey = createSkipjackKey();

        keyChain[size - 1] = lastKey;
        for (int i = size - 2; i >= 0; i--) {
            keyChain[i] = createNextKey(keyChain[i + 1]);
        }

        return keyChain;
    }

    public static boolean checkLastKeyIntegrity(byte[] firstKey, int firstKeyIndex, byte[] newKey, int newKeyIndex) {
        for (int i = newKeyIndex; i > firstKeyIndex; i--) {
            newKey = createNextKey(newKey);
        }

        return Arrays.equals(newKey, firstKey);
    }

    public static byte[] getKeyForTimeSlot(byte[] lastKey, int lastKeyIndex, int firstKeyIndex) {
        for (int i = lastKeyIndex; i > firstKeyIndex; i--) {
            lastKey = createNextKey(lastKey);
        }

        return lastKey;
    }

    /**
     * Retorna String com comprimento len com espacos brancos
     *
     * @param len : comprimento da string de saida
     * @return : string de espacos
     */
    public static String makeBlankString(
            int len) {
        char[] buf = new char[len];

        for (int i = 0; i != buf.length; i++) {
            buf[i] = ' ';
        }

        return new String(buf);
    }
    static final String HEXES = "0123456789ABCDEF";

    public static String getHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    public static byte[] MACLayerGlobalKey() {
        if (keyData == null) {
            keyData = createSkipjackKey();
        }
        return keyData;
    }

    public static byte[] MACLayerGlobalIV() {
        if (ivData == null) {
            ivData = createIV(BLOCK_SIZE);
        }
        return ivData;
    }

    public static byte[] createEncryptionKeyFromKey(byte[] key) {
        CMac mic = new CMac(new SkipjackEngine(), BLOCK_SIZE * 8);
        byte[] buffer = new byte[BLOCK_SIZE];
        mic.init(new KeyParameter(key));
        mic.update(key, 0, key.length);
        mic.doFinal(buffer, 0);
        return Arrays.copyOf(buffer, KEY_SIZE);

    }

    public static byte[] createMACKeyFromKey(byte[] key) {
        CMac mic = new CMac(new SkipjackEngine(), BLOCK_SIZE * 8);
        byte[] buffer = new byte[BLOCK_SIZE];
        mic.init(new KeyParameter(key));
        mic.update(key, 0, key.length);
        mic.doFinal(buffer, 0);
        return Arrays.copyOf(buffer, KEY_SIZE);

    }
    public static Key createSkipjackKeyObject() throws Exception {

            KeyGenerator key_g = KeyGenerator.getInstance(CYPHER_ALGORITHM, "BC");
            SecretKey k = key_g.generateKey();
            return k;

    }
}
