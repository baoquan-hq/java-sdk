package com.baoquan.jsdk;

import com.alibaba.fastjson.JSONObject;
import com.baoquan.jsdk.comm.*;
import com.baoquan.jsdk.exceptions.ClientException;
import com.baoquan.jsdk.exceptions.ServerException;
import com.baoquan.jsdk.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.bouncycastle.util.io.pem.PemReader;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaoquanClient {

    private String host = "https://api.baoquan.com";

    private String version = "v3";

    private String accessKey;

    private RequestIdGenerator requestIdGenerator = new DefaultRequestIdGenerator();

    private String pemPath;

    private byte[] privateKeyData;

    public byte[] getPrivateKeyData() {
        return privateKeyData;
    }

    public void setPrivateKeyData(byte[] privateKeyData) {
        this.privateKeyData = privateKeyData;
    }

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
        return json("attestations/text", payloadMap, null, ResultModel.class);
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
        return file("attestations/download", payloadMap);
    }

    public ResultModel downloadPdf(String ano) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("ano", ano);
        return json("attestations/pdf/download", payloadMap,null,ResultModel.class);
    }


    public ResultModel attestationInfo(String ano) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("ano", ano);
        return json("attestations", payloadMap, null, ResultModel.class);
    }

//    public String attestationAccessUrl(String ano) throws ServerException {
//        String signature = "";
//        long tonce = System.currentTimeMillis();
//        try {
//            Map map = new TreeMap() {
//                {
//                    put("access_key", accessKey);
//                    put("tonce", tonce);
//                    put("no", ano);
//
//                }
//            };
//            String data = Utils.objectToJson(map);
//            signature = Utils.sign(privateKeyData, data);
//        } catch (Exception e) {
//            throw new ServerException("", "签名失败", System.currentTimeMillis());
//        }
//        return String.format("%s/attestations/%s?accessKey=%s&signature=%s&tonce=%d", getHost(), ano, getAccessKey(), signature, tonce);
//    }

    public ResultModel createAttestationWithUrlConfirm(UrlAttestationStep2Param payload) throws ServerException {
        Map<String, Object> payloadMap = buildCreateAttestation4UrlConfirmPayloadMap(payload);
        return json("attestations/url/confirm", payloadMap, null, ResultModel.class);
    }

    public ResultModel downloadImgWithUrlAttestation(UrlAttestationStep2Param payload) throws ServerException {
        Map<String, Object> payloadMap = buildCreateAttestation4UrlConfirmPayloadMap(payload);
        return json("attestations/url/img", payloadMap, null, ResultModel.class);
    }

    public ResultModel getAttestationUrlInfo(UrlAttestationStep2Param payload) throws ServerException {
        Map<String, Object> payloadMap = buildCreateAttestation4UrlConfirmPayloadMap(payload);
        return json("attestations/url/info", payloadMap, null, ResultModel.class);
    }

    public ResultModel createAttestationWithUrl(UrlAttestationParam payload) throws ServerException {
        Map<String, Object> payloadMap = buildCreateAttestation4UrlPayloadMap(payload);
        return json("attestations/url", payloadMap, null, ResultModel.class);
    }

    public ResultModel createProcessToken(ProcessAttestationParam payload) throws ServerException {
        Map<String, Object> payloadMap = buildCreateAttestationPayloadMap(payload);
        return json("process/token", payloadMap, null, ResultModel.class);
    }

    public JSONObject getTaskList(Integer page_num, Integer page_size) throws ServerException {
        Map<String, Object> payloadMap = new HashMap();
        payloadMap.put("pageNum", page_num + "");
        payloadMap.put("pageSize", page_size + "");
        return this.Tojson("task/list", payloadMap, (Map)null);
    }

    public JSONObject taskAdd(String mark_id) throws ServerException {
        Map<String, Object> payloadMap = new HashMap();
        payloadMap.put("markId", mark_id);
        return this.Tojson("task/add", payloadMap, (Map)null);
    }

    public DownloadAttestationInfo taskDownload(String download_type,String mark_id) throws ServerException {
        Map<String, Object> payloadMap = new HashMap();
        payloadMap.put("downloadType", download_type);
        payloadMap.put("markId", mark_id);
        return this.Tofile("task/download", payloadMap);
    }

    public JSONObject taskRetry(String mark_id) throws ServerException {
        Map<String, Object> payloadMap = new HashMap();
        payloadMap.put("markId", mark_id);
        return this.Tojson("task/retry", payloadMap, (Map)null);
    }
    public JSONObject taskSearch(String mark_id) throws ServerException {
        Map<String, Object> payloadMap = new HashMap();
        payloadMap.put("markId", mark_id);
        return this.Tojson("task/search", payloadMap, (Map)null);
    }

    public ResultModel getProcessInfo(String ano) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("ano", ano);
        return json("process/info", payloadMap, null, ResultModel.class);
    }

    public ResultModel stopProcess(String ano) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("ano", ano);
        return json("process/stop", payloadMap, null, ResultModel.class);
    }


    public ResultModel cancelProcess(String ano) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("ano", ano);
        return json("process/cancel", payloadMap, null, ResultModel.class);
    }

    public ResultModel createMusicAttestation(MusicAttestationParam payload) throws ServerException {
        Map<String, Object> payloadMap = buildCreateMusicAttestationPayloadMap(payload);
        return json("attestations/music", payloadMap, null, ResultModel.class);
    }

    public ResultModel getMusicAttestationInfo(String ano) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("ano", ano);
        return json("music/info", payloadMap, null, ResultModel.class);
    }

    private Map<String, Object> buildCreateAttestation4UrlConfirmPayloadMap(UrlAttestationStep2Param payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
//        payloadMap.put("unique_id", payload.getUnique_id());
//        payloadMap.put("template_id", payload.getTemplate_id());
//        payloadMap.put("identities", payload.getIdentities());
        payloadMap.put("no", payload.getNo());
        return payloadMap;
    }

    private Map<String, Object> buildCreateAttestation4UrlPayloadMap(UrlAttestationParam payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("unique_id", payload.getUnique_id());
        payloadMap.put("template_id", payload.getTemplate_id());
        payloadMap.put("identities", payload.getIdentities());
        payloadMap.put("factoids", payload.getFactoids());
        payloadMap.put("url", payload.getUrl());
        payloadMap.put("callBackUrl", payload.getCallBackUrl());
        payloadMap.put("evidenceLabel", payload.getEvidenceLabel());
        payloadMap.put("evidenceName", payload.getEvidenceName());
        payloadMap.put("mode", payload.getMode());
        return payloadMap;
    }

    private Map<String, Object> buildCreateAttestationPayloadMap(ProcessAttestationParam payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("unique_id", payload.getUnique_id());
        payloadMap.put("template_id", payload.getTemplate_id());
        payloadMap.put("identities", payload.getIdentities());
        payloadMap.put("factoids", payload.getFactoids());
        payloadMap.put("evidenceType", payload.getEvidenceType());
        payloadMap.put("nodeGroup", payload.getNodeGroup());
        return payloadMap;
    }

    private Map<String, Object> buildCreateMusicAttestationPayloadMap(MusicAttestationParam payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("unique_id", payload.getUnique_id());
        payloadMap.put("template_id", payload.getTemplate_id());
        payloadMap.put("identities", payload.getIdentities());
        payloadMap.put("factoids", payload.getFactoids());
        payloadMap.put("url", payload.getUrl());
        payloadMap.put("platform", payload.getPlatform());
        payloadMap.put("song", payload.getSong());
        payloadMap.put("singer", payload.getSinger());
        payloadMap.put("album", payload.getAlbum());
        return payloadMap;
    }


    private Map<String, Object> buildCreateAttestationPayloadMap(BaseAttestationPayloadParam payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("unique_id", payload.getUnique_id());
        payloadMap.put("template_id", payload.getTemplate_id());
        payloadMap.put("identities", payload.getIdentities());
        payloadMap.put("factoids", payload.getFactoids());
        return payloadMap;
    }

    private Map<String, Object> buildCreateImportArticleAddMap(ImportArticleAddParam importArticleAddParam) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("title", importArticleAddParam.getTitle());
        payloadMap.put("body", importArticleAddParam.getBody());
        payloadMap.put("postTime", importArticleAddParam.getPostTime());
        return payloadMap;
    }

    private Map<String, Object> buildCreateUrlArticleAddMap(UrlArticleAddParam urlArticleAddParam) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("platform", urlArticleAddParam.getPlatform());
        payloadMap.put("url", urlArticleAddParam.getUrl());
        return payloadMap;
    }

    private Map<String, Object> buildCreateDoMonitorImgMap(DoMonitorParam doMonitorParam) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("resourceList", doMonitorParam.getResourceList());
        payloadMap.put("callbackUrl", doMonitorParam.getCallbackUrl());
        return payloadMap;
    }

    private Map<String, Object> buildCreateJudicialPayloadMap(EvidenceJudicialParam payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("anos", payload.getAnos());
        payloadMap.put("name", payload.getName());
        payloadMap.put("evidenceDesc", payload.getEvidenceDesc());
        payloadMap.put("evidenceUseType", payload.getEvidenceUseType());
        payloadMap.put("address", payload.getAddress());
        payloadMap.put("userName", payload.getUserName());
        payloadMap.put("userPhone", payload.getUserPhone());

        return payloadMap;
    }

    private Map<String, Object> buildCreateNotarizationPayloadMap(EvidenceNotarizationParam payload) {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("anos", payload.getAnos());
        payloadMap.put("name", payload.getName());
        payloadMap.put("address", payload.getAddress());
        payloadMap.put("userName", payload.getUserName());
        payloadMap.put("userPhone", payload.getUserPhone());

        return payloadMap;
    }

    public String attestationAccessUrl(String ano) throws ServerException {
        String signature = "";
        long tonce = System.currentTimeMillis();
        try {
            Map map = new TreeMap() {
                {
                    put("access_key", accessKey);
                    put("tonce", tonce);
                    put("no", ano);

                }
            };
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

    private Map<String, ByteArrayBody> buildMonitorFile(ByteArrayBody attachment) {
        Map<String, ByteArrayBody> streamBodyMap = new HashMap<String, ByteArrayBody>();
        if(null == attachment){
            throw new IllegalArgumentException("Please upload the file");
        }
        if (StringUtils.isEmpty(attachment.getFilename())) {
            throw new IllegalArgumentException("attachment filename can not be empty");
        }
        streamBodyMap.put("imagefile", attachment);
        return streamBodyMap;
    }

    private JSONObject Tojson(String apiName, Map<String, Object> payload, Map<String, ByteArrayBody> streamBodyMap) throws ServerException {
        String requestId = this.requestIdGenerator.createRequestId();
        CloseableHttpResponse closeableHttpResponse = this.post(requestId, apiName, payload, streamBodyMap);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        HttpEntity httpEntity = closeableHttpResponse.getEntity();

        String response;
        try {
            response = IOUtils.toString(httpEntity.getContent(), Consts.UTF_8);
            closeableHttpResponse.close();
        } catch (IOException var12) {
            throw new ClientException(var12);
        }

        if (statusCode == 200) {
            try {
                JSONObject responseObject = JSONObject.parseObject(response);
                return responseObject;
            } catch (Exception var11) {
                throw new ServerException(requestId, "Unknown error", System.currentTimeMillis());
            }
        } else {
            throw new ServerException(requestId, response, System.currentTimeMillis());
        }
    }


    public  <T> T json(String apiName, Map<String, Object> payload, Map<String, ByteArrayBody> streamBodyMap, Class<T> responseClass) throws ServerException {
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
        if (statusCode == HttpStatus.SC_OK) {
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
        } else {
            throw new ServerException(requestId, response, System.currentTimeMillis());
        }

    }

    private DownloadAttestationInfo file(String apiName, Map<String, Object> payload) throws ServerException {
        String requestId = requestIdGenerator.createRequestId();
        CloseableHttpResponse closeableHttpResponse = post(requestId, apiName, payload, null);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        if (statusCode != HttpStatus.SC_OK) {
            String response;
            try {
                response = IOUtils.toString(httpEntity.getContent(), Consts.UTF_8);
            } catch (Exception e) {
                throw new ClientException(e);
            }
            throw new ServerException(requestId, response, System.currentTimeMillis());
        }
        DownloadAttestationInfo downloadFile = new DownloadAttestationInfo();
        Header header = closeableHttpResponse.getFirstHeader(MIME.CONTENT_DISPOSITION);
        Pattern pattern = Pattern.compile(".*filename=(.*).*");
        Matcher matcher = pattern.matcher(header.getValue());
        if (matcher.matches()) {
            downloadFile.setFileName(matcher.group(1));
        }
        try {
            downloadFile.setFileInputStream(httpEntity.getContent());
        } catch (IOException e) {
            throw new ServerException(requestId, e.getMessage(), System.currentTimeMillis());
        }
        return downloadFile;
    }

    private DownloadAttestationInfo Tofile(String apiName, Map<String, Object> payload) throws ServerException {
        String requestId = requestIdGenerator.createRequestId();
        CloseableHttpResponse closeableHttpResponse = post(requestId, apiName, payload, null);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        if (statusCode != HttpStatus.SC_OK) {
            String response;
            try {
                response = IOUtils.toString(httpEntity.getContent(), Consts.UTF_8);
            } catch (Exception e) {
                throw new ClientException(e);
            }
            throw new ServerException(requestId, response, System.currentTimeMillis());
        }
        DownloadAttestationInfo downloadFile = new DownloadAttestationInfo();
        String ctype=closeableHttpResponse.getFirstHeader("Content-Type").toString();
        if(null!=ctype && ctype.contains("application/json")){
             downloadFile.setFileType("application/json");
        }else{
             downloadFile.setFileType("application/zip");
        }
       /*  Header header = closeableHttpResponse.getFirstHeader(MIME.CONTENT_DISPOSITION);
        Pattern pattern = Pattern.compile(".*filename=(.*).*");
        Matcher matcher = pattern.matcher(header.getValue());
        if (matcher.matches()) {
            downloadFile.setFileName(matcher.group(1));
        }*/
        try {
            downloadFile.setFileInputStream(httpEntity.getContent());
        } catch (IOException e) {
            throw new ServerException(requestId, e.getMessage(), System.currentTimeMillis());
        }
        return downloadFile;
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
            for (String name : streamBodyMap.keySet()) {
                multipartEntityBuilder.addPart(name, streamBodyMap.get(name));
            }
        }
        httpPost.setEntity(multipartEntityBuilder.build());
        // execute http json
//        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpClient closeableHttpClient = createSSLClientDefault();
        CloseableHttpResponse closeableHttpResponse;
        try {
            closeableHttpResponse = closeableHttpClient.execute(httpPost);
        } catch (IOException e) {
            throw new ClientException(e);
        }
        return closeableHttpResponse;
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    private void checkSha256(String sha256) {
        if (StringUtils.isBlank(sha256) || sha256.length() != 64 || !sha256.matches("[a-z0-9]*")) {
            throw new ClientException("invalid sha256 hash!");
        }
    }

    public ResultModel monitorImgAdd(ByteArrayBody imagefile) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        Map<String, ByteArrayBody> streamBodyMap = buildMonitorFile(imagefile);
        return json("monitorImgAdd", payloadMap, streamBodyMap, ResultModel.class);
    }

    public ResultModel importArticleAdd(ImportArticleAddParam importArticleAddParam) throws ServerException {
        Map<String, Object> payloadMap = buildCreateImportArticleAddMap(importArticleAddParam);
        return json("importArticleAdd", payloadMap, null, ResultModel.class);
    }

    public ResultModel urlArticleAdd(UrlArticleAddParam urlArticleAddParam) throws ServerException {
        Map<String, Object> payloadMap = buildCreateUrlArticleAddMap(urlArticleAddParam);
        return json("urlArticleAdd", payloadMap, null, ResultModel.class);
    }

    public ResultModel doMonitorImg(DoMonitorParam doMonitorParam) throws ServerException {
        Map<String, Object> payloadMap = buildCreateDoMonitorImgMap(doMonitorParam);
        return json("doMonitorImg", payloadMap, null, ResultModel.class);
    }

    public ResultModel doMonitorArticle(DoMonitorParam doMonitorParam) throws ServerException {
        Map<String, Object> payloadMap = buildCreateDoMonitorImgMap(doMonitorParam);
        return json("doMonitorArticle", payloadMap, null, ResultModel.class);
    }

    public ResultModel createJudicial(EvidenceJudicialParam evidenceJudicialParam) throws ServerException {
        Map<String, Object> payloadMap = buildCreateJudicialPayloadMap(evidenceJudicialParam);
        return json("evidence/judicial", payloadMap, null, ResultModel.class);
    }

    public ResultModel createNotarization(EvidenceNotarizationParam evidenceNotarizationParam) throws ServerException {
        Map<String, Object> payloadMap = buildCreateNotarizationPayloadMap(evidenceNotarizationParam);
        return json("evidence/notarization", payloadMap, null, ResultModel.class);
    }

    public ResultModel uploadAttestationHashFile(String ano, ByteArrayBody attachment) throws ServerException {
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("ano", ano);
        Map<String, ByteArrayBody> streamBodyMap = buildFile(attachment);
        return json("evidence/hash", payloadMap, streamBodyMap, ResultModel.class);
    }
}
