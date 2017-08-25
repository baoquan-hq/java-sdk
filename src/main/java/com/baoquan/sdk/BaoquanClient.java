package com.baoquan.sdk;

import com.baoquan.sdk.exceptions.ClientException;
import com.baoquan.sdk.exceptions.ServerException;
import com.baoquan.sdk.pojos.payload.*;
import com.baoquan.sdk.pojos.response.*;
import com.baoquan.sdk.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bouncycastle.openssl.PEMParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class BaoquanClient {

    private String host = "https://baoquan.com";
    //private String host = "http://127.0.0.1:8080"

    private String version = "v1";

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
        PEMParser pemParser = new PEMParser(new InputStreamReader(new FileInputStream(pemPath)));
        this.privateKeyData = pemParser.readPemObject().getContent();
        pemParser.close();
        this.pemPath = pemPath;
    }

    /**
     * create attestation with no attachments
     *
     * @param payload {@link CreateAttestationPayload}
     * @return {@link CreateAttestationResponse}
     * @throws ServerException {@link ServerException}
     */
    public CreateAttestationResponse createAttestation(CreateAttestationPayload payload) throws ServerException {
        return createAttestation(payload, null);
    }

    /**
     * create attestation with sha256
     *
     * @param payload payload
     * @param sha256  the algorithm is SHA256WithRSA
     * @return {@link CreateAttestationResponse}
     * @throws ServerException {@link ServerException}
     */
    public CreateAttestationResponse createAttestationWithSha256(CreateAttestationPayload payload, String sha256) throws ServerException {
        checkSha256(sha256);
        payload.setSha256(sha256);
        Map<String, Object> payloadMap = buildCreateAttestationPayloadMap(payload, null);
        return json("attestations/hash", payloadMap, null, CreateAttestationResponse.class);
    }


    /**
     * create attestation with sha256
     *
     * @param payload payload
     * @param url     url
     * @return {@link CreateAttestationResponse}
     * @throws ServerException {@link ServerException}
     */
    public CreateAttestationResponse createAttestationWithUrl(CreateAttestationPayload payload, String url) throws ServerException {
        payload.setUrl(url);
        Map<String, Object> payloadMap = buildCreateAttestationPayloadMap(payload, null);
        return json("attestations/url", payloadMap, null, CreateAttestationResponse.class);
    }


    /**
     * create attestation with attachments, one factoid can have more than one attachments
     *
     * @param payload     {@link ContractPayload}
     * @param attachments attachments
     * @return {@link String}
     * @throws ServerException {@link ServerException}
     */
    public UploadSignatureResponse uploadSignature(ContractPayload payload, Map<String, List<ByteArrayBody>> attachments) throws ServerException {

        Map<String, Object> payloadMap = builduploadSignaturePayloadMap(payload);
        Map<String, List<ByteArrayBody>> streamBodyMap = buildStreamBodyMap(attachments);
        return json("contract/signature", payloadMap, streamBodyMap, UploadSignatureResponse.class);
    }

    /**
     * create attestation with attachments, one factoid can have more than one attachments
     *
     * @param payload payload
     * @return {@link String}
     * @throws ServerException {@link ServerException}
     */
    public ResultResponse setSignatureDefaultId(SignaturePayload payload) throws ServerException {
        Map<String, Object> payloadMap = buildSetSignatureDefaultIdPayloadMap(payload);
//        Map<String, List<ByteArrayBody>> streamBodyMap = buildStreamBodyMap(attachments);
        return json("contract/signature/default", payloadMap, null, ResultResponse.class);
    }


    /**
     * create attestation with attachments, one factoid can have more than one attachments
     *
     * @return {@link String}
     * @throws ServerException {@link ServerException}
     */
    public String sendVerifyCode() throws ServerException {
        Map<String, Object> payloadMap = buildSendVerifyCodePayloadMap();
//        Map<String, List<ByteArrayBody>> streamBodyMap = buildStreamBodyMap(attachments);
        return json("contract/verify_code", payloadMap, null, null);
    }

    /**
     * create attestation with attachments, one factoid can have more than one attachments
     *
     * @param payload payload
     * @return {@link String}
     * @throws ServerException {@link ServerException}
     */
    public String deleteSignature(SignaturePayload payload) throws ServerException {
        Map<String, Object> payloadMap = buildDeleteSignaturePayloadMap(payload);
//        Map<String, List<ByteArrayBody>> streamBodyMap = buildStreamBodyMap(attachments);
        return json("contract/signature/delete", payloadMap, null, null);
    }

    /**
     * create attestation with attachments, one factoid can have more than one attachments
     *
     * @param payload payload
     * @return {@link String}
     * @throws ServerException {@link ServerException}
     */
    public String signatureList(SignaturePayload payload) throws ServerException {
        Map<String, Object> payloadMap = buildSignatureListPayloadMap(payload);
//        Map<String, List<ByteArrayBody>> streamBodyMap = buildStreamBodyMap(attachments);
        return json("contract/signature/list", payloadMap, null, null);
    }


    /**
     * create attestation with attachments, one factoid can have more than one attachments
     *
     * @param attachments attachments
     * @param payload     {@link ContractPayload}
     * @return {@link String}
     * @throws ServerException {@link ServerException}
     */
    public UploadContractResponse uploadContract(ContractPayload payload, Map<String, List<ByteArrayBody>> attachments) throws ServerException {
        // checkCreateAttestationPayload(payload);
        Map<String, Object> payloadMap = buildUploadContractPayloadMap(payload);
        Map<String, List<ByteArrayBody>> streamBodyMap = buildStreamBodyMap(attachments);
        return json("contract/uploadPdf", payloadMap, streamBodyMap, UploadContractResponse.class);
    }

    /**
     * create attestation with attachments, one factoid can have more than one attachments
     *
     * @param payload {@link ContractPayload}
     * @return {@link String}
     * @throws ServerException {@link ServerException}
     */
    public ResultResponse setContractDetail(ContractPayload payload) throws ServerException {

        Map<String, Object> payloadMap = buildSetContractPayloadMap(payload);
        return json("contract/setDetail", payloadMap, null, ResultResponse.class);
    }


    /**
     * create attestation with attachments, one factoid can have more than one attachments
     *
     * @param payload     {@link CreateAttestationPayload}
     * @param attachments attachments map, the key is the index of corresponding factoid in factoid set
     * @return {@link CreateAttestationResponse}
     * @throws ServerException {@link ServerException}
     */
    public CreateAttestationResponse createAttestation(CreateAttestationPayload payload, Map<String, List<ByteArrayBody>> attachments) throws ServerException {
        checkCreateAttestationPayload(payload);
        Map<String, Object> payloadMap = buildCreateAttestationPayloadMap(payload, attachments);
        Map<String, List<ByteArrayBody>> streamBodyMap = buildStreamBodyMap(attachments);
        return json("attestations", payloadMap, streamBodyMap, CreateAttestationResponse.class);
    }

    /**
     * add factoids to attestation with no attachments
     *
     * @param payload {@link AddFactoidsPayload}
     * @return {@link AddFactoidsResponse}
     * @throws ServerException {@link ServerException}
     */
    public AddFactoidsResponse addFactoids(AddFactoidsPayload payload) throws ServerException {
        return addFactoids(payload, null);
    }

    /**
     * add factoids to attestation with attachments, one factoid can have more than one attachments
     *
     * @param payload     {@link AddFactoidsPayload}
     * @param attachments attachments map, the key is the index of corresponding factoid in factoid set
     * @return {@link AddFactoidsResponse}
     * @throws ServerException {@link ServerException}
     */
    public AddFactoidsResponse addFactoids(AddFactoidsPayload payload, Map<String, List<ByteArrayBody>> attachments) throws ServerException {
        checkAddFactoidsPayload(payload);
        Map<String, Object> payloadMap = buildAddFactoidsPayloadMap(payload, attachments);
        Map<String, List<ByteArrayBody>> streamBodyMap = buildStreamBodyMap(attachments);
        return json("factoids", payloadMap, streamBodyMap, AddFactoidsResponse.class);
    }

    /**
     * get attestation raw data
     *
     * @param ano    attestation no
     * @param fields attestation field
     * @return {@link GetAttestationResponse}
     * @throws ServerException {@link ServerException}
     */
    public GetAttestationResponse getAttestation(String ano, List<String> fields) throws ServerException {
        if (StringUtils.isEmpty(ano)) {
            throw new IllegalArgumentException("ano can not be null");
        }
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("ano", ano);
        payload.put("fields", fields);
        return json("attestation", payload, null, GetAttestationResponse.class);
    }

    /**
     * get attestation raw data
     *
     * @return {@link GetAttestationResponse}
     * @throws ServerException {@link ServerException}
     */
    public ListSignatureResponse listSignature() throws ServerException {
        Map<String, Object> payload = new HashMap<String, Object>();
        return json("contract/signature/list", payload, null, ListSignatureResponse.class);
    }

    /**
     * download attestation file which is hashed to block chain
     *
     * @param ano attestation id
     * @return {@link DownloadFile}
     * @throws ServerException {@link ServerException}
     */
    public DownloadFile downloadAttestation(String ano) throws ServerException {
        if (StringUtils.isEmpty(ano)) {
            throw new IllegalArgumentException("ano can not be null");
        }
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("ano", ano);
        return file("attestation/download", payload);
    }

    /**
     * apply ca
     *
     * @param payload {@link ApplyCaPayload}
     * @param seal    the seal of enterprise
     * @return {@link ApplyCaResponse}
     * @throws ServerException {@link ServerException}
     */
    public ApplyCaResponse applyCa(ApplyCaPayload payload, ByteArrayBody seal) throws ServerException {
        checkApplyCaPayload(payload);
        Map<String, Object> payloadMap = buildApplyCaPayloadMap(payload);
        Map<String, List<ByteArrayBody>> streamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        if (seal != null) {
            streamBodyMap.put("seal", Collections.singletonList(seal));
        }
        return json("cas", payloadMap, streamBodyMap, ApplyCaResponse.class);
    }


    public UserKycResponse userKyc(String phoneNumber, String name, String idCard) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("phone", phoneNumber);
        payloadMap.put("name", name);
        payloadMap.put("idCard", idCard);
        return json("users/kyc", payloadMap, null, UserKycResponse.class);
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


    private void checkCreateAttestationPayload(CreateAttestationPayload payload) {
        if (payload == null) {
            throw new IllegalArgumentException("payload can not be null");
        }
        if (StringUtils.isEmpty(payload.getUniqueId())) {
            throw new IllegalArgumentException("payload.uniqueId can not be empty");
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

    private Map<String, Object> builduploadSignaturePayloadMap(ContractPayload payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        return payloadMap;
    }

    private Map<String, Object> buildSendVerifyCodePayloadMap() {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("phone", "15811111111");
        payloadMap.put("ecs_status", "DONE");
        return payloadMap;
    }


    private Map<String, Object> buildSendupdateContractPayloadMap() {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("phone", "15811111111");
        payloadMap.put("phone", "15811111111");
        payloadMap.put("phone", "15811111111");
        payloadMap.put("phone", "15811111111");

        payloadMap.put("ecs_status", "DONE");
        return payloadMap;
    }

    private Map<String, Object> buildSetSignatureDefaultIdPayloadMap(SignaturePayload payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();

        payloadMap.put("signature_id", payload.getSignature_id());
        return payloadMap;
    }


    private Map<String, Object> buildDeleteSignaturePayloadMap(SignaturePayload payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("signature_id", payload.getSignature_id());
        return payloadMap;
    }

    private Map<String, Object> buildSignatureListPayloadMap(SignaturePayload payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        return payloadMap;
    }


    private Map<String, Object> buildUploadContractPayloadMap(ContractPayload payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        return payloadMap;
    }


    private Map<String, Object> buildSetContractPayloadMap(ContractPayload payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("title", payload.getTitle());
        payloadMap.put("end_at", payload.getEnd_at());
        payloadMap.put("remark", payload.getRemark());
        payloadMap.put("userPhones", payload.getUserPhones());
        payloadMap.put("contract_id", payload.getContract_id());
        return payloadMap;
    }


    private Map<String, Object> buildCreateAttestationPayloadMap(CreateAttestationPayload payload, Map<String, List<ByteArrayBody>> attachments) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("unique_id", payload.getUniqueId());
        payloadMap.put("template_id", payload.getTemplateId());
        payloadMap.put("identities", payload.getIdentities());
        payloadMap.put("factoids", payload.getFactoids());
        payloadMap.put("completed", payload.isCompleted());
        if (StringUtils.isNotBlank(payload.getOpenStatusKey())) {
            payloadMap.put("openStatusKey", payload.getOpenStatusKey());
        }
        String sha256 = payload.getSha256();
        if (StringUtils.isNotBlank(sha256)) {
            payloadMap.put("sha256", sha256);
        }
        String url = payload.getUrl();
        if (StringUtils.isNoneBlank(url)) {
            payloadMap.put("url", url);
        }
        payloadMap.put("attachments", buildChecksum(payload, attachments));
        return payloadMap;
    }

    private Map<String, Object> buildAddFactoidsPayloadMap(AddFactoidsPayload payload, Map<String, List<ByteArrayBody>> attachments) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("ano", payload.getAno());
        payloadMap.put("factoids", payload.getFactoids());
        payloadMap.put("completed", payload.isCompleted());
        payloadMap.put("attachments", buildChecksum(payload, attachments));
        return payloadMap;
    }

    private Map<String, Object> buildApplyCaPayloadMap(ApplyCaPayload payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
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

    private Map<String, List<Object>> buildChecksum(AttestationPayload payload, Map<String, List<ByteArrayBody>> attachments) {
        Map<String, List<Object>> payloadAttachments = new HashMap<String, List<Object>>();
        if (attachments != null) {
            Map<String, Map<String, Map<String, List<String>>>> signs = payload.getSigns();
            for (int i = 0; i < payload.getFactoids().size(); i++) {
                String is = "" + i;
                List<ByteArrayBody> byteArrayBodies = attachments.get(is);
                Map<String, Map<String, List<String>>> iSigns = null;
                if (signs != null) {
                    iSigns = signs.get(is);
                }
                if (byteArrayBodies != null && byteArrayBodies.size() > 0) {
                    List<Object> objects = new ArrayList<Object>();
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
                            Map<String, Object> checksumSign = new HashMap<String, Object>();
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

    private <T> T json(String apiName, Map<String, Object> payload, Map<String, List<ByteArrayBody>> streamBodyMap, Class<T> responseClass) throws ServerException {
        String requestId = requestIdGenerator.createRequestId();
        CloseableHttpResponse closeableHttpResponse = post(requestId, apiName, payload, streamBodyMap);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        if (statusCode != HttpStatus.SC_OK) {
            throwServerException(requestId, httpEntity);
        }
        String response;
        try {
            response = IOUtils.toString(httpEntity.getContent(), Consts.UTF_8);
            closeableHttpResponse.close();
        } catch (IOException e) {
            throw new ClientException(e);
        }
        System.out.println(response);
        T responseObject;
        try {
            responseObject = Utils.jsonToObject(response, responseClass);
        } catch (IOException e) {
            throw new ServerException(requestId, "Unknown error", System.currentTimeMillis());
        }
        return responseObject;
    }

    private String jsonfile(String apiName, Map<String, Object> payload, ByteArrayBody streamBody) throws ServerException {
        String requestId = requestIdGenerator.createRequestId();
        CloseableHttpResponse closeableHttpResponse = postfile(requestId, apiName, payload, streamBody);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        if (statusCode != HttpStatus.SC_OK) {
            throwServerException(requestId, httpEntity);
        }
        String response;
        try {
            response = IOUtils.toString(httpEntity.getContent(), Consts.UTF_8);
            closeableHttpResponse.close();
        } catch (IOException e) {
            throw new ClientException(e);
        }
        return response;
//        T responseObject;
//        try {
//            responseObject = Utils.jsonToObject(response, responseClass);
//        } catch (IOException e) {
//            throw new ServerException(requestId, "Unknown error", System.currentTimeMillis());
//        }
//        return responseObject;
    }

    private DownloadFile file(String apiName, Map<String, Object> payload) throws ServerException {
        String requestId = requestIdGenerator.createRequestId();
        CloseableHttpResponse closeableHttpResponse = post(requestId, apiName, payload, null);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        if (statusCode != HttpStatus.SC_OK) {
            throwServerException(requestId, httpEntity);
        }
        DownloadFile downloadFile = new DownloadFile();
        Header header = closeableHttpResponse.getFirstHeader(MIME.CONTENT_DISPOSITION);
        Pattern pattern = Pattern.compile(".*filename=\"(.*)\".*");
        Matcher matcher = pattern.matcher(header.getValue());
        if (matcher.matches()) {
            downloadFile.setFileName(matcher.group(1));
        }
        try {
            downloadFile.setFile(httpEntity.getContent());
        } catch (IOException e) {
            throw new ServerException(requestId, e.getMessage(), System.currentTimeMillis());
        }
        return downloadFile;
    }

    private CloseableHttpResponse post(String requestId, String apiName, Map<String, Object> payload, Map<String, List<ByteArrayBody>> streamBodyMap) {
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
                .addTextBody("payload", payloadString, ContentType.create("text/plain", Consts.UTF_8)) // avoid chinese garbled
                .addTextBody("signature", signature);
        if (streamBodyMap != null) {
            for (String name :
                    streamBodyMap.keySet()) {
                List<ByteArrayBody> list = streamBodyMap.get(name);
                for (ByteArrayBody item :
                        list) {
                    multipartEntityBuilder.addPart(name, item);
                }
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


    private CloseableHttpResponse postfile(String requestId, String apiName, Map<String, Object> payload, ByteArrayBody streamBody) {
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
                .addTextBody("payload", payloadString, ContentType.create("text/plain", Consts.UTF_8)) // avoid chinese garbled
                .addTextBody("signature", signature);
//    if (streamBodyMap != null) {
//      for (String name :
//              streamBodyMap.keySet()) {
//        List<ByteArrayBody> list = streamBodyMap.get(name);
//        for (ByteArrayBody item :
//                list) {
        // multipartEntityBuilder.addPart("file", streamBody);
        multipartEntityBuilder.addPart(payload.get("filename").toString(), streamBody);
//        }
//      }
//    }
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


    private void throwServerException(String requestId, HttpEntity httpEntity) throws ServerException {
        String response;
        try {
            response = IOUtils.toString(httpEntity.getContent(), Consts.UTF_8);
        } catch (IOException e) {
            throw new ClientException(e);
        }
        ExceptionResponse exceptionResponse;
        try {
            exceptionResponse = Utils.jsonToObject(response, ExceptionResponse.class);
        } catch (IOException e) {
            throw new ServerException(requestId, "Unknown error", System.currentTimeMillis());
        }
        throw new ServerException(exceptionResponse.getRequest_id(), exceptionResponse.getMessage(), exceptionResponse.getTimestamp());
    }

    private void checkSha256(String sha256) {
        if (StringUtils.isBlank(sha256) || sha256.length() != 64 || !sha256.matches("[a-z0-9]*")) {
            throw new ClientException("invalid sha256 hash!");
        }
    }


    /**
     * send VerifyCode
     *
     * @param contractId contractId
     * @param phone      phone
     * @return CloseableHttpResponse
     * @throws ServerException ServerException
     */
    public ResultResponse sendVerifyCode(String contractId, String phone) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("contract_id", contractId);
        payloadMap.put("phone", phone);
        return json("contract/verifyCode", payloadMap, null, ResultResponse.class);
    }


    /**
     * @param contractId contractId
     * @param phone      phone
     * @param verifyCode verifyCode
     * @param ecsStatus  ecsStatus
     * @param page       page
     * @param posX       posX
     * @param posY       posY
     * @return CloseableHttpResponse
     * @throws ServerException ServerException
     */
    public ResultResponse signContract(String contractId, String phone, String verifyCode, String ecsStatus, String page, String posX, String posY, String templateId, Map<String, String> identities,
                                       List<PayloadFactoid> factoids, Boolean completed) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("contract_id", contractId);
        payloadMap.put("phone", phone);
        payloadMap.put("verify_code", verifyCode);
        payloadMap.put("ecs_status", ecsStatus);
        payloadMap.put("page", page);
        payloadMap.put("posX", posX);
        payloadMap.put("posY", posY);
        payloadMap.put("template_id", templateId);
        payloadMap.put("identities",identities);
        payloadMap.put("factoids",factoids);
        payloadMap.put("completed", completed);

        return json("contract/sign", payloadMap, null, ResultResponse.class);
    }
}
