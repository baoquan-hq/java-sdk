package com.baoquan.jsdk;

import com.baoquan.jsdk.Enum.IdentityTypeEnum;
import com.baoquan.jsdk.comm.*;
import com.baoquan.jsdk.exceptions.ServerException;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.*;

public class BaoquanClientTest {

    private BaoquanClient client;

    @Before
    public void initClient() {
        client = new BaoquanClient();
        client.setHost("http://127.0.0.1:9090");
        client.setAccessKey("ceshikey");
        client.setVersion("v3");
        try {
            client.setPemPath("C:\\Users\\LA\\Desktop\\private_key.pem");
//            client.setPemPath("E:\\dataqin\\jsdk\\src\\main\\resources\\key.pem");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCreateAttestation() {
        HashAttestationParam payload = new HashAttestationParam();
        payload.setTemplate_id("ceshiuserdata");
        payload.setUnique_id(randomUniqueId());
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "15817112383");
        payload.setIdentities(identities);
        List<PayloadFactoidParam> factoids = new ArrayList<PayloadFactoidParam>();
        PayloadFactoidParam factoid = new PayloadFactoidParam();
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("file");
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        factoid.setData(map);
        map.put("file_name", "李三");
        map.put("size", "330124199501017791");
        factoids.add(factoid);
        payload.setFactoids(factoids);

        ResultModel response = null;
        try {
            response = client.createAttestation(payload);
        } catch (ServerException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(response.getData());
    }


    @Test
    public void testCreateAttestationWithSha256() {
        HashAttestationParam payload = new HashAttestationParam();

        payload.setTemplate_id("ceshisha256");
        payload.setUnique_id(randomUniqueId());
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "15851112383");
        payload.setIdentities(identities);
        List<PayloadFactoidParam> factoids = new ArrayList<PayloadFactoidParam>();
        PayloadFactoidParam factoid = new PayloadFactoidParam();
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("file");
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        factoid.setData(map);
        map.put("file_name", "李三");
        map.put("size", "330124199501017791");
        factoids.add(factoid);
        payload.setFactoids(factoids);

        payload.setSha256("654c71176b207401445fdd471f5e021f65af50d7361bf828e5b1c19c89b977b0");
        ResultModel response = null;
        try {
            response = client.createAttestationWithSha256(payload);
        } catch (ServerException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(response.getData());
    }


    @Test
    public void testCreateAttestationWithFile() throws IOException {
        BaseAttestationPayloadParam payload = new BaseAttestationPayloadParam();
        payload.setTemplate_id("11234dfcsafac");
        payload.setUnique_id(randomUniqueId());
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "15857112383");
        payload.setIdentities(identities);
        List<PayloadFactoidParam> factoids = new ArrayList<PayloadFactoidParam>();
        PayloadFactoidParam factoid = new PayloadFactoidParam();
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("file");
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        factoid.setData(map);
        map.put("file_name", "李三");
        map.put("size", "330124199501017791");
        factoids.add(factoid);
        payload.setFactoids(factoids);
        ResultModel response = null;
        InputStream inputStream = new FileInputStream("C:\\workspace\\workspace\\work\\jsdk\\src\\main\\java\\com\\baoquan\\jsdk\\RequestIdGenerator.java");
        ;
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "RequestIdGenerator.java");

        try {
            response = client.createAttestationWithFile(payload, byteArrayBody);
        } catch (ServerException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(response.getData());
    }


    @Test
    public void createAttestationWithUrl() throws ServerException, InterruptedException {
        String url = "http://www.qq.com/";
        UrlAttestationParam payload = new UrlAttestationParam();
        // 设置保全唯一码
        payload.setUnique_id(randomUniqueId());
        // 设置模板id
        payload.setTemplate_id("4g8kLrgrr8AGTXKqUzW1rc");
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "429006198507104214");
        payload.setIdentities(identities);

        List<PayloadFactoidParam> factoids = new ArrayList<PayloadFactoidParam>();
        PayloadFactoidParam factoid = new PayloadFactoidParam();

        LinkedHashMap<String, String> loanDataMap = new LinkedHashMap<String, String>();

        loanDataMap.put("web_address", url);
        loanDataMap.put("web_name", "web_name");
        factoid.setData(loanDataMap);
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("website");
        factoids.add(factoid);
        payload.setFactoids(factoids);

        payload.setUrl("http://www.baoquan.com/");
        payload.setMode(1);

        ResultModel response = client.createAsyAttestationWithUrl(payload);
        Assert.assertNotNull(response.getData());
    }


    @Test
    public void createAttestationWithUrlConfirm() throws ServerException, InterruptedException {
        UrlAttestationStep2Param payload = new UrlAttestationStep2Param();
        // 设置保全唯一码
        payload.setUnique_id(randomUniqueId());
        // 设置模板id
        payload.setTemplate_id("4g8kLrgrr8AGTXKqUzW1rc");
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "429006198507104214");
        payload.setIdentities(identities);
        payload.setNo("414384432619753472");

        ResultModel response = client.createAttestationWithUrlConfirm(payload);
        Assert.assertNotNull(response.getData());
    }
    @Test
    public void downloadImgWithUrlAttestation() throws ServerException, InterruptedException {
        UrlAttestationStep2Param payload = new UrlAttestationStep2Param();
        // 设置保全唯一码
        payload.setUnique_id(randomUniqueId());
        // 设置模板id
        payload.setTemplate_id("4g8kLrgrr8AGTXKqUzW1rc");
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "429006198507104214");
        payload.setIdentities(identities);
        payload.setNo("414384432619753472");

        ResultModel response = client.downloadImgWithUrlAttestation(payload);
        Assert.assertNotNull(response.getData());
    }
    @Test
    public void testdownloadFile() {
        DownloadAttestationInfo response = null;
        try {
            response = client.downloadFile("394544545871728641");
        } catch (ServerException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(response.getNo());
    }


    private String randomUniqueId() {
        return UUID.randomUUID().toString();
    }

    private String randomIDCard() {
        String a = Integer.toString(rand(110000, 650999));

        String yy = Integer.toString(rand(1950, 2000));
        String mm = Integer.toString(rand(3, 12));
        String dd = Integer.toString(rand(1, 30));
        if (mm.length() != 2) {
            mm = "0" + mm;
        }
        if (dd.length() != 2) {
            dd = "0" + dd;
        }

        String b = Integer.toString(rand(0, 999));
        if (b.length() == 1) {
            b = "00" + b;
        } else if (b.length() == 2) {
            b = "0" + b;
        }

        String s = a + yy + mm + dd + b;
        //System.out.println(s);

        int[] nums = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        int m = 0;
        for (int i = 0; i < s.length(); i++) {
            String c = s.substring(i, i + 1);
            int n = Integer.parseInt(c);
            int n2 = nums[i];
            m += n * n2;
        }
        m = m % 11;

        String[] codes = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        String code = codes[m];
        s += code;
        return s;
    }

    private String randomPhone() {
        return "130" + rand(10000000, 99999999);
    }

    private int rand(int min, int max) {
        return (int) ((double) (max - min + 1) * Math.random() + (double) min);
    }

}
