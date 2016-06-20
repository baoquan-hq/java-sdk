package com.baoquan.sdk;

import com.baoquan.sdk.exceptions.ClientException;
import com.baoquan.sdk.exceptions.ServerException;
import com.baoquan.sdk.pojos.payload.*;
import com.baoquan.sdk.pojos.response.AddFactoidsResponse;
import com.baoquan.sdk.pojos.response.ApplyCaResponse;
import com.baoquan.sdk.pojos.response.CreateAttestationResponse;
import com.baoquan.sdk.pojos.response.ExceptionResponse;
import com.baoquan.sdk.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class BaoquanClient {

  private String host = "https://baoquan.com";

  private String version = "v1";

  private String accessKey;

  private RequestIdGenerator requestIdGenerator = new DefaultRequestIdGenerator();

  private String pemPath;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public RequestIdGenerator getRequestIdGenerator() {
    return requestIdGenerator;
  }

  public void setRequestIdGenerator(RequestIdGenerator requestIdGenerator) {
    if (requestIdGenerator == null) {
      throw new IllegalArgumentException("requestIdGenerator can not be null");
    }
    this.requestIdGenerator = requestIdGenerator;
  }

  public String getPemPath() {
    return pemPath;
  }

  public void setPemPath(String pemPath) {
    this.pemPath = pemPath;
  }

  /**
   * create attestation with no attachments
   * @param payload {@link CreateAttestationPayload}
   * @return {@link CreateAttestationResponse}
   * @throws ServerException
   */
  public CreateAttestationResponse createAttestation(CreateAttestationPayload payload) throws ServerException {
    return createAttestation(payload, null);
  }

  /**
   * create attestation with attachments, one factoid can have more than one attachments
   * @param payload {@link CreateAttestationPayload}
   * @param attachments attachments map, the key is the index of corresponding factoid in factoid set
   * @return {@link CreateAttestationResponse}
   * @throws ServerException
   */
  public CreateAttestationResponse createAttestation(CreateAttestationPayload payload, Map<String, List<ByteArrayBody>> attachments) throws ServerException {
    checkCreateAttestationPayload(payload);
    Map<String, Object> payloadMap = buildCreateAttestationPayloadMap(payload, attachments);
    Map<String, ByteArrayBody> streamBodyMap = buildStreamBodyMap(attachments);
    return post("attestations", payloadMap, streamBodyMap, CreateAttestationResponse.class);
  }

  /**
   * add factoids to attestation with no attachments
   * @param payload {@link AddFactoidsPayload}
   * @return {@link AddFactoidsResponse}
   * @throws ServerException
   */
  public AddFactoidsResponse addFactoids(AddFactoidsPayload payload) throws ServerException {
    return addFactoids(payload, null);
  }

  /**
   * add factoids to attestation with attachments, one factoid can have more than one attachments
   * @param payload {@link AddFactoidsPayload}
   * @param attachments attachments map, the key is the index of corresponding factoid in factoid set
   * @return {@link AddFactoidsResponse}
   * @throws ServerException
   */
  public AddFactoidsResponse addFactoids(AddFactoidsPayload payload, Map<String, List<ByteArrayBody>> attachments) throws ServerException {
    checkAddFactoidsPayload(payload);
    Map<String, Object> payloadMap = buildAddFactoidsPayloadMap(payload, attachments);
    Map<String, ByteArrayBody> streamBodyMap = buildStreamBodyMap(attachments);
    return post("factoids", payloadMap, streamBodyMap, AddFactoidsResponse.class);
  }

  /**
   * apply ca
   * @param payload {@link ApplyCaPayload}
   * @param seal the seal of enterprise
   * @return {@link ApplyCaResponse}
   * @throws ServerException
   */
  public ApplyCaResponse applyCa(ApplyCaPayload payload, ByteArrayBody seal) throws ServerException {
    checkApplyCaPayload(payload);
    if (payload.getType() == CaType.ENTERPRISE && seal == null) {
      throw new IllegalArgumentException("seal can not be null when ca type is enterprise");
    }
    Map<String, Object> payloadMap = buildApplyCaPayloadMap(payload);
    Map<String, ByteArrayBody> streamBodyMap = new HashMap<>();
    if (seal != null) {
      streamBodyMap.put("seal", seal);
    }
    return post("cas", payloadMap, streamBodyMap, ApplyCaResponse.class);
  }

  private void checkCreateAttestationPayload(CreateAttestationPayload payload) {
    if (payload == null) {
      throw new IllegalArgumentException("payload can not be null");
    }
    if (StringUtils.isEmpty(payload.getTemplateId())) {
      throw new IllegalArgumentException("payload.templateId can not be empty");
    }
    if (payload.getIdentities() == null || payload.getIdentities().size() == 0) {
      throw new IllegalArgumentException("payload.identities can not be empty");
    }
    if (payload.getFactoids() == null || payload.getFactoids().size() == 0) {
      throw new IllegalArgumentException("payload.factoids can not be empty");
    }
  }

  private void checkAddFactoidsPayload(AddFactoidsPayload payload) {
    if (payload == null) {
      throw new IllegalArgumentException("payload can not be null");
    }
    if (StringUtils.isEmpty(payload.getAno())) {
      throw new IllegalArgumentException("payload.ano can not be empty");
    }
    if (payload.getFactoids() == null || payload.getFactoids().size() == 0) {
      throw new IllegalArgumentException("payload.factoids can not be empty");
    }
  }

  private void checkApplyCaPayload(ApplyCaPayload payload) {
    if (payload == null) {
      throw new IllegalArgumentException("payload can not be null");
    }
    if (payload.getType() == null) {
      throw new IllegalArgumentException("payload.type can not be null");
    }
    if (payload.getType() == CaType.ENTERPRISE) {
      if (StringUtils.isEmpty(payload.getName())) {
        throw new IllegalArgumentException("payload.name can not be empty");
      }
      if (StringUtils.isEmpty(payload.getIcCode())) {
        throw new IllegalArgumentException("payload.icCode can not be empty");
      }
      if (StringUtils.isEmpty(payload.getOrgCode())) {
        throw new IllegalArgumentException("payload.orgCode can not be empty");
      }
      if (StringUtils.isEmpty(payload.getTaxCode())) {
        throw new IllegalArgumentException("payload.taxCode can not be empty");
      }
    }
    if (StringUtils.isEmpty(payload.getLinkName())) {
      throw new IllegalArgumentException("payload.linkName can not be empty");
    }
    if (StringUtils.isEmpty(payload.getLinkEmail())) {
      throw new IllegalArgumentException("payload.linkEmail can not be empty");
    }
    if (StringUtils.isEmpty(payload.getLinkPhone())) {
      throw new IllegalArgumentException("payload.linkPhone can not be empty");
    }
    if (StringUtils.isEmpty(payload.getLinkIdCard())) {
      throw new IllegalArgumentException("payload.linkIdCard can not be empty");
    }
  }

  private Map<String, Object> buildCreateAttestationPayloadMap(CreateAttestationPayload payload, Map<String, List<ByteArrayBody>> attachments) {
    Map<String, Object> payloadMap = new HashMap<>();
    payloadMap.put("template_id", payload.getTemplateId());
    payloadMap.put("identities", payload.getIdentities());
    payloadMap.put("factoids", payload.getFactoids());
    payloadMap.put("completed", payload.isCompleted());
    payloadMap.put("attachments", buildChecksum(payload, attachments));
    return payloadMap;
  }

  private Map<String, Object> buildAddFactoidsPayloadMap(AddFactoidsPayload payload, Map<String, List<ByteArrayBody>> attachments) {
    Map<String, Object> payloadMap = new HashMap<>();
    payloadMap.put("ano", payload.getAno());
    payloadMap.put("factoids", payload.getFactoids());
    payloadMap.put("completed", payload.isCompleted());
    payloadMap.put("attachments", buildChecksum(payload, attachments));
    return payloadMap;
  }

  private Map<String, Object> buildApplyCaPayloadMap(ApplyCaPayload payload) {
    Map<String, Object> payloadMap = new HashMap<>();
    payloadMap.put("type", payload.getType());
    payloadMap.put("name", payload.getName());
    payloadMap.put("ic_code", payload.getIcCode());
    payloadMap.put("org_code", payload.getOrgCode());
    payloadMap.put("tax_code", payload.getTaxCode());
    payloadMap.put("link_name", payload.getLinkName());
    payloadMap.put("link_id_card", payload.getLinkIdCard());
    payloadMap.put("link_phone", payload.getLinkPhone());
    payloadMap.put("link_email", payload.getLinkEmail());
    return payloadMap;
  }

  private Map<String, ByteArrayBody> buildStreamBodyMap(Map<String, List<ByteArrayBody>> attachments) {
    Map<String, ByteArrayBody> streamBodyMap = new HashMap<>();
    if (attachments != null) {
      attachments.forEach((i, list)->list.forEach(item->streamBodyMap.put(String.format("attachments[%s][]", i), item)));
    }
    return streamBodyMap;
  }

  private Map<String, List<Object>> buildChecksum(AttestationPayload payload, Map<String, List<ByteArrayBody>> attachments) {
    Map<String, List<Object>> payloadAttachments = new HashMap<>();
    if (attachments != null) {
      Map<String, Map<String, Map<String, List<String>>>> signs = payload.getSigns();
      for (int i = 0; i < payload.getFactoids().size(); i++) {
        String is = "" + i;
        List<ByteArrayBody> byteArrayBodies = attachments.get(is);
        Map<String, Map<String, List<String>>> iSigns = signs.get(is);
        if (byteArrayBodies != null && byteArrayBodies.size() > 0) {
          List<Object> objects = new ArrayList<>();
          for (int j = 0; j < byteArrayBodies.size(); j++) {
            ByteArrayBody byteArrayBody = byteArrayBodies.get(j);
            String checksum;
            try {
              ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
              byteArrayBody.writeTo(byteArrayOutputStream);
              checksum = Utils.checksum(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
              throw new ClientException(e);
            }
            String js = "" + j;
            Map<String, List<String>> jSigns = null;
            if (iSigns != null) {
              jSigns = iSigns.get(js);
            }
            if (jSigns == null) {
              objects.add(checksum);
            } else {
              Map<String, Object> checksumSign = new HashMap<>();
              checksumSign.put("checksum", checksum);
              checksumSign.put("sign", jSigns);
              objects.add(checksumSign);
            }
          }
          payloadAttachments.put(is, objects);
        }
      }
    }
    return payloadAttachments;
  }

  private <T>T post(String apiName, Map<String, Object> payload, Map<String, ByteArrayBody> streamBodyMap, Class<T> responseClass) throws ServerException {
    String payloadString;
    try {
      payloadString = Utils.objectToJson(payload);
    } catch (JsonProcessingException e) {
      throw new ClientException(e);
    }
    String httpMethod = "POST";
    String path = String.format("/api/%s/%s", version, apiName);
    String requestId = requestIdGenerator.createRequestId();
    if (StringUtils.isEmpty(requestId)) {
      throw new ClientException("request id can not be empty");
    }
    int tonce = (int) (System.currentTimeMillis()/1000);
    // build the data to sign
    String data = httpMethod + path + requestId + accessKey + tonce + payloadString;
    String signature;
    try {
      signature = Utils.sign(pemPath, data);
    } catch (Exception e) {
      throw new ClientException(e);
    }
    // build post request
    String uri = host + path;
    HttpPost httpPost = new HttpPost(uri);
    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
            .create()
            .setMode(HttpMultipartMode.RFC6532) // avoid chinese garbled
            .setCharset(Consts.UTF_8) // avoid chinese garbled
            .addTextBody("request_id", requestId)
            .addTextBody("access_key", accessKey)
            .addTextBody("tonce", "" + tonce)
            .addTextBody("payload", payloadString, ContentType.create("text/plain", Consts.UTF_8)) // avoid chinese garbled
            .addTextBody("signature", signature);
    if (streamBodyMap != null) {
      streamBodyMap.forEach(multipartEntityBuilder::addPart);
    }
    httpPost.setEntity(multipartEntityBuilder.build());
    // execute http post
    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    CloseableHttpResponse closeableHttpResponse;
    try {
      closeableHttpResponse = closeableHttpClient.execute(httpPost);
    } catch (IOException e) {
      throw new ClientException(e);
    }
    int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
    HttpEntity httpEntity = closeableHttpResponse.getEntity();
    String response;
    try {
      response = IOUtils.toString(httpEntity.getContent(), "UTF-8");
      closeableHttpResponse.close();
      closeableHttpClient.close();
    } catch (IOException e) {
      throw new ClientException(e);
    }
    if (statusCode != HttpStatus.SC_OK) {
      ExceptionResponse exceptionResponse;
      try {
        exceptionResponse = Utils.jsonToObject(response, ExceptionResponse.class);
      } catch (IOException e) {
        throw new ServerException(requestId, "Unknown error");
      }
      throw new ServerException(exceptionResponse.getRequest_id(), exceptionResponse.getMessage());
    }
    T responseObject;
    try {
      responseObject = Utils.jsonToObject(response, responseClass);
    } catch (IOException e) {
      throw new ServerException(requestId, "Unknown error");
    }
    return responseObject;
  }
}
