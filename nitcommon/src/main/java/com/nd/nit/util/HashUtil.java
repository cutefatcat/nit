package com.nd.nit.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static String calculateHash(InputStream fis){
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] dataBytes = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(dataBytes)) > 0) {
                md.update(dataBytes, 0, bytesRead);
            }
            byte[] mdBytes = md.digest();

            // Перевод контрольную сумму в виде массива байт в
            // шестнадцатеричное представление
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mdBytes.length; i++) {
                sb.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException | IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String calculateHash(String str){
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());

//            byte[] dataBytes = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = fis.read(dataBytes)) > 0) {
//                md.update(dataBytes, 0, bytesRead);
//            }
            byte[] mdBytes = md.digest();

            // Перевод контрольную сумму в виде массива байт в
            // шестнадцатеричное представление
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mdBytes.length; i++) {
                sb.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
