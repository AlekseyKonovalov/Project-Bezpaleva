package com.bezPalevaServer.Services;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Decryptor {

    Cipher dcipher;

    public  boolean checkSecretKey(String secretKey) throws IOException, BadPaddingException, IllegalBlockSizeException {

        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(secretKey);
        byte[] utf8 = dcipher.doFinal(dec);
        secretKey = new String(utf8, "UTF8");

        if(secretKey.equals("%@wFGQMt9MN?|mb8ay*ok4@qq7o}5IS2|bj")) return true;
        else return false;
    }

    public Decryptor() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        SecretKey key = new SecretKeySpec("~v3*JxsX".getBytes(), "DES");

        dcipher = Cipher.getInstance("DES");

        dcipher.init(Cipher.DECRYPT_MODE, key);

    }
}
