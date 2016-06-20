package com.baoquan.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.openssl.PEMParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * Created by sbwdlihao on 6/18/16.
 */
public class Utils {

  /**
   * use rsa 256 to sign data
   * @param pemPath private key pem file path
   * @param data data to sign
   * @return signed data
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   * @throws InvalidKeyException
   * @throws SignatureException
   */
  public static String sign(String pemPath, String data) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
    if (StringUtils.isEmpty(pemPath)) {
      throw new IllegalArgumentException("pem path can not be empty");
    }
    if (data == null) {
      return null;
    }
    PEMParser pemParser = new PEMParser(new InputStreamReader(new FileInputStream(pemPath)));
    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(pemParser.readPemObject().getContent());
    pemParser.close();
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    Signature signature = Signature.getInstance("SHA256WithRSA");
    signature.initSign(privateKey);
    signature.update(data.getBytes("UTF-8"));
    return Base64.getEncoder().encodeToString(signature.sign());
  }

  /**
   * use sha256 to create the checksum of input bytes
   * @param bytes input bytes
   * @return the hex of checksum
   */
  public static String checksum(byte[] bytes) {
    byte[] digestBytes = DigestUtils.getDigest("SHA-256").digest(bytes);
    return Hex.encodeHexString(digestBytes);
  }

  /**
   * convert object to json string
   * @param object object
   * @return json string
   * @throws JsonProcessingException
   */
  public static String objectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(object);
  }

  /**
   * convert json string to target class object
   * @param json json string
   * @param t t
   * @param <T> T
   * @return target object
   * @throws IOException
   */
  public static <T>T jsonToObject(String json, Class<T> t) throws IOException {
    if (json == null) {
      return null;
    }
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, t);
  }
}
