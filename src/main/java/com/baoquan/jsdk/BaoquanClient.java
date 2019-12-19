package com.baoquan.jsdk;

import com.baoquan.jsdk.comm.BaseAttestationPayloadParam;
import com.baoquan.jsdk.comm.DownloadAttestationInfo;
import com.baoquan.jsdk.comm.HashAttestationParam;
import com.baoquan.jsdk.comm.ResultModel;
import com.baoquan.jsdk.exceptions.ClientException;
import com.baoquan.jsdk.exceptions.ServerException;
import com.baoquan.jsdk.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.*;
import java.util.*;


public class BaoquanClient {

    private String host = "https://baoquan.com";
//    private String host = "http://127.0.0.1:8088";

    private String version = "v3";

    private String accessKey;

    private RequestIdGenerator requestIdGenerator = new DefaultRequestIdGenerator();

    private String pemPath;

    private byte[] privateKeyData;

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

    public void setPemPath(String pemPath) throws IOException {
        if (StringUtils.isEmpty(pemPath)) {
            throw new IllegalArgumentException("pemPath can not be empty");
        }
        PemReader pemReader = new PemReader(new InputStreamReader(new FileInputStream(pemPath)));
        this.privateKeyData = pemReader.readPemObject().getContent();
        pemReader.close();
        this.pemPath = pemPath;
    }

    /**
     * create attestation with attachments, one factoid can have more than one attachments
     *
     * @param payload {@link BaseAttestationPayloadParam}
     * @return {@link ResultModel}
     * @throws ServerException {@link ServerException}
     */
    public ResultModel createAttestation(BaseAttestationPayloadParam payload) throws ServerException {
        Map<String, Object> payloadMap = buildCreateAttestationPayloadMap(payload);
        return json("attestations", payloadMap, null, ResultModel.class);
    }

    /**
     * create attestation with sha256
     *
     * @param payload payload
     * @return {@link ResultModel}
     * @throws ServerException {@link ServerException}
     */
    public ResultModel createAttestationWithSha256(HashAttestationParam payload) throws ServerException {

        Map<String, Object> payloadMap = buildCreateAttestationPayloadMap(payload);
        checkSha256(payload.getSha256());
        payloadMap.put("sha256", payload.getSha256());
        return json("attestations/hash", payloadMap, null, ResultModel.class);
    }

    /**
     * create attestation with attachments, one factoid can have more than one attachments
     *
     * @param payload    {@link BaseAttestationPayloadParam}
     * @param attachment attachment
     * @return {@link ResultModel}
     * @throws ServerException {@link ServerException}
     */
    public ResultModel createAttestationWithFile(BaseAttestationPayloadParam payload, ByteArrayBody attachment) throws ServerException {
        Map<String, Object> payloadMap = buildCreateAttestationPayloadMap(payload);
        Map<String, ByteArrayBody> streamBodyMap = buildFile(attachment);
        return json("attestations/file", payloadMap, streamBodyMap, ResultModel.class);
    }

    public DownloadAttestationInfo downloadFile(String ano) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("ano", ano);
        return json("attestations/download", payloadMap, null, DownloadAttestationInfo.class);
    }


    private Map<String, Object> buildCreateAttestationPayloadMap(BaseAttestationPayloadParam payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("unique_id", payload.getUnique_id());
        payloadMap.put("template_id", payload.getTemplate_id());
        payloadMap.put("identities", payload.getIdentities());
        payloadMap.put("factoids", payload.getFactoids());
        return payloadMap;
    }

    public String attestationAccessUrl(String ano) throws ServerException {
        String signature = "";
        long tonce = System.currentTimeMillis();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("attestationId", ano);
            map.put("tonce", tonce);
            String data = Utils.objectToJson(map);
            signature = Utils.sign(privateKeyData, data);
        } catch (Exception e) {
            throw new ServerException("", "签名失败", System.currentTimeMillis());
        }
        return String.format("%s/attestations/%s?accessKey=%s&signature=%s&tonce=%d", getHost(), ano, getAccessKey(), signature, tonce);
    }

    private Map<String, List<ByteArrayBody>> buildStreamBodyMap(Map<String, List<ByteArrayBody>> attachments) {
        Map<String, List<ByteArrayBody>> streamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        if (attachments != null) {
            for (String i :
                    attachments.keySet()) {
                List<ByteArrayBody> list = attachments.get(i);
                for (ByteArrayBody item :
                        list) {
                    if (item.getContentType() != ContentType.DEFAULT_BINARY) {
                        throw new IllegalArgumentException("attachment content type is invalid");
                    }
                    if (StringUtils.isEmpty(item.getFilename())) {
                        throw new IllegalArgumentException("attachment filename can not be empty");
                    }
                }
                streamBodyMap.put(String.format("attachments[%s][]", i), list);
            }
        }
        return streamBodyMap;
    }


    private Map<String, ByteArrayBody> buildFile(ByteArrayBody attachment) {
        Map<String, ByteArrayBody> streamBodyMap = new HashMap<String, ByteArrayBody>();
        if (attachment.getContentType() != ContentType.DEFAULT_BINARY) {
            throw new IllegalArgumentException("attachment content type is invalid");
        }
        if (StringUtils.isEmpty(attachment.getFilename())) {
            throw new IllegalArgumentException("attachment filename can not be empty");
        }
        streamBodyMap.put("attachment", attachment);
        return streamBodyMap;
    }

//    public static void main(String[] args) {
//        String s = "https://www.baidu.com/s?ie=UTF-8&wd=java%20string%E9%95%BF%E5%BA%A6https://www.baidu.com/s?ie=UTF-8&wd=java%20string%E9%95%BF%E5%BA%A6" +
//                "";
//    }

    private <T> T json(String apiName, Map<String, Object> payload, Map<String, ByteArrayBody> streamBodyMap, Class<T> responseClass) throws ServerException {
        String requestId = requestIdGenerator.createRequestId();
        CloseableHttpResponse closeableHttpResponse = post(requestId, apiName, payload, streamBodyMap);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        String response;
        try {
            response = IOUtils.toString(httpEntity.getContent(), Consts.UTF_8);
            closeableHttpResponse.close();
        } catch (IOException e) {
            throw new ClientException(e);
        }
        T responseObject;
        if (responseClass == null) {
            return (T) response;
        }
        try {
            responseObject = Utils.jsonToObject(response, responseClass);
        } catch (IOException e) {
            throw new ServerException(requestId, "Unknown error", System.currentTimeMillis());
        }
        return responseObject;
    }


    private CloseableHttpResponse post(String requestId, String apiName, Map<String, Object> payload, Map<String, ByteArrayBody> streamBodyMap) {
        String path = String.format("/api/%s/%s", version, apiName);
        if (StringUtils.isEmpty(requestId)) {
            throw new ClientException("request id can not be empty");
        }
        if (StringUtils.isEmpty(accessKey)) {
            throw new ClientException("accessKey can not be empty");
        }
        int tonce = (int) (System.currentTimeMillis() / 1000);
        String payloadString;
        try {
            payloadString = Utils.objectToJson(payload);
        } catch (JsonProcessingException e) {
            throw new ClientException(e);
        }
        // build the data to sign
        String data = "POST" + path + requestId + accessKey + tonce + payloadString;
        String signature;
        try {
            signature = Utils.sign(privateKeyData, data);
        } catch (Exception e) {
            throw new ClientException(e);
        }
        // build json request
        String uri = host + path;
        HttpPost httpPost = new HttpPost(uri);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                .create()
                .setMode(HttpMultipartMode.RFC6532) // avoid chinese garbled
                .setCharset(Consts.UTF_8) // avoid chinese garbled
                .addTextBody("request_id", requestId)
                .addTextBody("access_key", accessKey)
                .addTextBody("tonce", "" + tonce)
                .addTextBody("payload", payloadString, ContentType.APPLICATION_JSON) // avoid chinese garbled
                .addTextBody("signature", signature);
        if (streamBodyMap != null) {
            for (String name :streamBodyMap.keySet()) {
                multipartEntityBuilder.addPart(name, streamBodyMap.get(name));
            }
        }
        httpPost.setEntity(multipartEntityBuilder.build());
        // execute http json
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse;
        try {
            closeableHttpResponse = closeableHttpClient.execute(httpPost);
        } catch (IOException e) {
            throw new ClientException(e);
        }
        return closeableHttpResponse;
    }


    private void checkSha256(String sha256) {
        if (StringUtils.isBlank(sha256) || sha256.length() != 64 || !sha256.matches("[a-z0-9]*")) {
            throw new ClientException("invalid sha256 hash!");
        }
    }


}
