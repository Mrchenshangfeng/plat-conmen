package com.hywisdom.platform.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPublicKey;

/**
 * @program: platform-common
 * @description:
 * @author: xfl
 * @CreateTime: 2019年09月28日 17:55
 **/
public class RSAUtils {


    //KeyPair is a simple holder for a key pair.
    private static final KeyPair keyPair = initKey();

    /**
     * 初始化方法，产生key pair，提供provider和random
     *
     * @return KeyPair instance
     */
    private static KeyPair initKey() {

        try {
            //添加provider
            Provider provider = new BouncyCastleProvider();
            Security.addProvider(provider);
            //产生用于安全加密的随机数
            SecureRandom random = new SecureRandom();

            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);
            generator.initialize(1024, random);
            return generator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成公钥
     *
     * @return public key字符串
     */
    public static String generateBase64PublicKey() {
        PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        //encodeBase64(): Encodes binary data using the base64
        //algorithm but does not chunk the output.
        //getEncoded():返回key的原始编码形式
        return new String(Base64.encodeBase64(publicKey.getEncoded()));
    }

    /**
     * 解密数据
     *
     * @param string 需要解密的字符串
     * @return 破解之后的字符串
     */
    public static String decryptBase64(String string) {
        //decodeBase64():将Base64数据解码为"八位字节”数据
        return new String(decrypt(Base64.decodeBase64(string.getBytes())));
    }

    private static byte[] decrypt(byte[] byteArray) {
        try {
            Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
            Security.addProvider(provider);
            //Cipher: 提供加密和解密功能的实例
            //transformation: "algorithm/mode/padding"
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            PrivateKey privateKey = keyPair.getPrivate();
            //初始化
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //doFinal(): 加密或者解密数据
            byte[] plainText = cipher.doFinal(byteArray);
            return plainText;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
