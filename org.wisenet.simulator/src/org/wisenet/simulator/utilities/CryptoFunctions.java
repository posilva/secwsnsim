package org.wisenet.simulator.utilities;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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

/**
 *
 * @author posilva
 */
public class CryptoFunctions {

    static boolean bypassBouncyCastle = false;
//    private static final String CYPHER_SUITE = "Skipjack/CTR/NoPadding";
    private static final String CYPHER_SUITE = "Skipjack/CFB/NoPadding";
    private static final String CYPHER_ALGORITHM = "Skipjack";
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public static final int BLOCK_SIZE = 8, MIC_SIZE = 4, KEY_SIZE = 10, MAC_SIZE = 8;
    private static byte[] keyData;
    private static byte[] ivData;

    /**
     *
     * @param data
     * @param key
     * @param iv
     * @return
     */
    public static byte[] cipherData(byte[] data, byte[] key, byte[] iv) {
        try {
            if (!bypassBouncyCastle) {
                SecretKeySpec key_spec = new SecretKeySpec(key, CYPHER_ALGORITHM);
                Cipher c = Cipher.getInstance(CYPHER_SUITE, "BC");
                c.init(Cipher.ENCRYPT_MODE, key_spec, new IvParameterSpec(iv));
                return c.doFinal(data);
            } else {
                return data;
            }
        } catch (NoSuchAlgorithmException e) {
            Utilities.handleException(e);
            System.exit(0);
        } catch (NoSuchPaddingException e) {
            Utilities.handleException(e);
        } catch (InvalidKeyException e) {
            Utilities.handleException(e);
        } catch (InvalidAlgorithmParameterException e) {
            Utilities.handleException(e);
        } catch (IllegalBlockSizeException e) {
            Utilities.handleException(e);
        } catch (BadPaddingException e) {
            Utilities.handleException(e);
        } catch (NoSuchProviderException e) {
            Utilities.handleException(e);
        }

        return null;
    }

    /**
     *
     * @param data
     * @param key
     * @param iv
     * @return
     */
    public static byte[] decipherData(byte[] data, byte[] key, byte[] iv) {
        try {
            if (!bypassBouncyCastle) {

                SecretKeySpec key_spec = new SecretKeySpec(key, CYPHER_ALGORITHM);
                Cipher c = Cipher.getInstance(CYPHER_SUITE, "BC");
                c.init(Cipher.DECRYPT_MODE, key_spec, new IvParameterSpec(iv));
                return c.doFinal(data);
            } else {
                return data;
            }
        } catch (NoSuchAlgorithmException e) {
            Utilities.handleException(e);
        } catch (NoSuchPaddingException e) {
            Utilities.handleException(e);
        } catch (InvalidKeyException e) {
            Utilities.handleException(e);
        } catch (InvalidAlgorithmParameterException e) {
            Utilities.handleException(e);
        } catch (IllegalBlockSizeException e) {
            Utilities.handleException(e);
        } catch (BadPaddingException e) {
            Utilities.handleException(e);
        } catch (NoSuchProviderException e) {
            Utilities.handleException(e);
        }
        return null;
    }

    /**
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] createMIC(byte[] data, byte[] key) {
        CMac mic = new CMac(new SkipjackEngine(), MIC_SIZE * 8);
        byte[] buffer = new byte[MIC_SIZE];
        mic.init(new KeyParameter(key));
        mic.update(data, 0, data.length);
        mic.doFinal(buffer, 0);
        return buffer;
    }

    /**
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] createMAC(byte[] data, byte[] key) {
        CMac mic = new CMac(new SkipjackEngine(), MAC_SIZE * 8);
        byte[] buffer = new byte[MAC_SIZE];
        mic.init(new KeyParameter(key));
        mic.update(data, 0, data.length);
        mic.doFinal(buffer, 0);
        return buffer;
    }

    /**
     *
     * @param counter
     * @return
     */
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

    /**
     *
     * @return
     */
    public static byte[] createSkipjackKey() {
        try {
            return createSkipjackKeyObject().getEncoded();
        } catch (Exception e) {
            Utilities.handleException(e);
            System.exit(0);
        }
        System.exit(0);
        return null;
    }

    /**
     *
     * @param payload
     * @param received_mic
     * @param key
     * @return
     */
    public static boolean verifyMessageIntegrity(byte[] payload, byte[] received_mic, byte[] key) {
        return Arrays.equals(received_mic, CryptoFunctions.createMIC(payload, key));
    }

    /**
     *
     * @param lastKey
     * @return
     */
    public static byte[] createNextKey(byte[] lastKey) {
        CMac mic = new CMac(new SkipjackEngine(), BLOCK_SIZE * 8);
        byte[] buffer = new byte[BLOCK_SIZE];
        mic.init(new KeyParameter(lastKey));
        mic.update(lastKey, 0, lastKey.length);
        mic.doFinal(buffer, 0);
        return Arrays.copyOf(buffer, KEY_SIZE);

    }

    /**
     *
     * @param size
     * @return
     */
    public static byte[][] createOneWayKeyChain(int size) {
        byte[][] keyChain = new byte[size][];
        byte[] lastKey = createSkipjackKey();

        keyChain[size - 1] = lastKey;
        for (int i = size - 2; i >= 0; i--) {
            keyChain[i] = createNextKey(keyChain[i + 1]);
        }

        return keyChain;
    }

    /**
     *
     * @param firstKey
     * @param firstKeyIndex
     * @param newKey
     * @param newKeyIndex
     * @return
     */
    public static boolean checkLastKeyIntegrity(byte[] firstKey, int firstKeyIndex, byte[] newKey, int newKeyIndex) {
        for (int i = newKeyIndex; i > firstKeyIndex; i--) {
            newKey = createNextKey(newKey);
        }

        return Arrays.equals(newKey, firstKey);
    }

    /**
     *
     * @param lastKey
     * @param lastKeyIndex
     * @param firstKeyIndex
     * @return
     */
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

    /**
     *
     * @param raw
     * @return
     */
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

    /**
     *
     * @return
     */
    public static byte[] MACLayerGlobalKey() {
        if (keyData == null) {
            keyData = createSkipjackKey();
        }
        return keyData;
    }

    /**
     *
     * @return
     */
    public static byte[] MACLayerGlobalIV() {
        if (ivData == null) {
            ivData = createIV(BLOCK_SIZE);
        }
        return ivData;
    }

    /**
     *
     * @param key
     * @return
     */
    public static byte[] createEncryptionKeyFromKey(byte[] key) {
        CMac mic = new CMac(new SkipjackEngine(), BLOCK_SIZE * 8);
        byte[] buffer = new byte[BLOCK_SIZE];
        mic.init(new KeyParameter(key));
        mic.update(key, 0, key.length);
        mic.doFinal(buffer, 0);
        return Arrays.copyOf(buffer, KEY_SIZE);

    }

    /**
     *
     * @param key
     * @return
     */
    public static byte[] createMACKeyFromKey(byte[] key) {
        CMac mic = new CMac(new SkipjackEngine(), BLOCK_SIZE * 8);
        byte[] buffer = new byte[BLOCK_SIZE];
        mic.init(new KeyParameter(key));
        mic.update(key, 0, key.length);
        mic.doFinal(buffer, 0);
        return Arrays.copyOf(buffer, KEY_SIZE);

    }

    /**
     *
     * @return
     * @throws Exception
     */
    public static Key createSkipjackKeyObject() throws Exception {

        KeyGenerator key_g = KeyGenerator.getInstance(CYPHER_ALGORITHM, "BC");
        SecretKey k = key_g.generateKey();
        return k;

    }

    /**
     *
     * @param payload
     * @param received_mac
     * @param key
     * @return
     */
    public static boolean verifyMessageIntegrityMAC(byte[] payload, byte[] received_mac, byte[] key) {
        return Arrays.equals(received_mac, CryptoFunctions.createMAC(payload, key));
    }

    /**
     *
     * @param data
     * @return
     */
    public static byte[] digestMD5(byte[] data) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(data);
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptoFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
