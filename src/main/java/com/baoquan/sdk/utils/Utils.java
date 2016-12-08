package com.baoquan.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Created by sbwdlihao on 6/18/16.
 */
public class Utils {

  /**
   * use rsa 256 to sign data
   * @param encodedKey private key bytes
   * @param data data to sign
   * @return signed data
   * @throws NoSuchAlgorithmException  NoSuchAlgorithmException
   * @throws InvalidKeySpecException InvalidKeySpecException
   * @throws InvalidKeyException InvalidKeyException
   * @throws SignatureException SignatureException
   * @throws UnsupportedEncodingException UnsupportedEncodingException
   */
  public static String sign(byte[] encodedKey, String data) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
    if (data == null) {
      return null;
    }
    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodedKey);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    Signature signature = Signature.getInstance("SHA256WithRSA");
    signature.initSign(privateKey);
    signature.update(data.getBytes("UTF-8"));
    return Base64.encodeBase64String(signature.sign());
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
   * @throws JsonProcessingException JsonProcessingException
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
   * @throws IOException IOException
   */
  public static <T>T jsonToObject(String json, Class<T> t) throws IOException {
    if (json == null) {
      return null;
    }
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, t);
  }
}
