package ua.dp.stud.createAccount.util;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hash {
    private static final String EXEPTION = "Exception: ";

    private static Cipher ecipher;
    private static Cipher dcipher;
    private AlgorithmParameterSpec paramSpec;
    private SecretKey key;
    private static final int ITERATIONCOUNT = 10;
    private static final String PASSPHRASE = "My Very Secret Password";
    private static final int RADIX = 16;
    // 8-byte Salt
    private static final byte FIRSTBYTE = (byte) 0xB2;
    private static final byte SECONDBYTE = (byte) 0x12;
    private static final byte THIRDBYTE = (byte) 0xD5;
    private static final byte FIFTHBYTE = (byte) 0x44;
    private static final byte SIXTHBYTE = (byte) 0x21;
    private static final byte SEVENTHBYTE = (byte) 0xC3;
    private static final Logger LOG = Logger.getLogger(Hash.class.getName());

    private static final byte[] SALT = {
            FIRSTBYTE, SECONDBYTE, THIRDBYTE, FIRSTBYTE,
            FIFTHBYTE, SIXTHBYTE, SEVENTHBYTE, SEVENTHBYTE
    };

    public Hash() {
        try {
            // create a user-chosen password that can be used with password-based encryption (PBE)
            // provide password, salt, iteration count for generating PBEKey of fixed-key-size PBE ciphers
            KeySpec keySpec = new PBEKeySpec(PASSPHRASE.toCharArray(), SALT, ITERATIONCOUNT);

            // create a secret (symmetric) key using PBE with MD5 and DES 
            key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            // construct a parameter set for password-based encryption as defined in the PKCS #5 standard
            paramSpec = new PBEParameterSpec(SALT, ITERATIONCOUNT);

            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());
        } catch (InvalidKeySpecException ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
        } catch (NoSuchAlgorithmException ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
        } catch (NoSuchPaddingException ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
        }
    }

    public String getCrypt(String input) {
        try {
            // initialize the ciphers with the given key
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            String encrypted = this.encrypt(input);
            String hex = this.strToHex(encrypted);

            return hex;
        } catch (InvalidAlgorithmParameterException ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
            return null;
        } catch (InvalidKeyException ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
            return null;
        }

    }

    public String getDecrypt(String input) {
        try {
            // initialize the ciphers with the given key
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            String encrypted = this.hexToStr(input);
            return this.decrypt(encrypted);
        } catch (InvalidAlgorithmParameterException ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
            return null;
        } catch (InvalidKeyException ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
            return null;
        }
    }

    private String encrypt(String str) {
        try {
            // encode the string into a sequence of bytes using the named charset
            // storing the result into a new byte array. 
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);
            // encode to base64
            enc = BASE64EncoderStream.encode(enc);
            return new String(enc);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
        }
        return null;
    }

    private String decrypt(String str) {
        try {
            // decode with base64 to get bytes
            byte[] dec = BASE64DecoderStream.decode(str.getBytes());
            byte[] utf8 = dcipher.doFinal(dec);
            // create new string based on the specified charset
            return new String(utf8, "UTF8");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, EXEPTION, ex);
        }
        return null;
    }

    private String strToHex(String input) {
        byte[] ba = input.getBytes();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < ba.length; i++) {
            str.append(String.format("%x", ba[i]));
        }
        return str.toString().toUpperCase();
    }

    private String hexToStr(String hex) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), RADIX));
        }
        return str.toString();
    }
}