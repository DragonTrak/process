package com.esa2000.process.util.certUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * ****************************************************************************
 * AES加解密算法
 *
 * @author arix04
 */

public class AES {
//    private static Logger logger = Logger.getLogger(AES.class);
    private static final String DEFUALT_PASSWORD = "azt#Sign@4091QdL";

    public static byte[] encrypt(String password, byte[] bytes) {
        byte[] result = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec("Aze@tmf09~SdfdsL".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            result = cipher.doFinal(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] decrypt(String password, byte[] bytes) {
        byte[] result = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec("Aze@tmf09~SdfdsL".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            result = cipher.doFinal(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] encrypt(byte[] sSrc) throws Exception {
        return encrypt(DEFUALT_PASSWORD, sSrc);
    }

    public static byte[] decrypt(byte[] sSrc) throws Exception {
        return decrypt(DEFUALT_PASSWORD, sSrc);
    }



}
