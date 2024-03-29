package com.baoquan.jsdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baoquan.jsdk.Enum.IdentityTypeEnum;
import com.baoquan.jsdk.comm.*;
import com.baoquan.jsdk.exceptions.ServerException;
import com.baoquan.jsdk.utils.Utils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BaoquanClientTest {

    private BaoquanClient client;

    @Before
    public void initClient() {
        client = new BaoquanClient();
//        client.setHost("https://api.baoquan.com");
 //       client.setHost("http://192.168.3.98:9090");
        client.setHost("http://localhost:9090");
        client.setAccessKey("ceshikey");
        client.setVersion("v3");
        try {
//            client.setPemPath("C:\\Users\\LA\\Desktop\\private_key.pem");
 //           client.setPemPath("E:\\dataqin\\java-sdk\\src\\main\\resources\\key.pem");
            client.setPemPath("D:\\zl\\key.pem");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test11() throws ServerException {
        JSONObject response = client.getTaskList(1,2);
        System.out.println(client.getHost()+"  "+client.getAccessKey()+" "+client.getPemPath()+"  "+client.getVersion()+"  "+client.getRequestIdGenerator().createRequestId());
        System.out.println(response);
    }

    @Test
    public void testCreateAttestation() {
        BaseAttestationPayloadParam payload = new HashAttestationParam();
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
        map.put("file_name", "https://jx.tmall.com/?spm=a219t.7664554.1998457203.159.hWZb4X&ali_trackid=2:mm_122806507_911000261_109921750097:1584691132_121_1943880412");
        map.put("size", "330124199501017791");
        factoids.add(factoid);
        payload.setFactoids(factoids);
        try {
            ResultModel response = client.createAttestation(payload);
            System.out.println(Utils.objectToJson(response.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        payload.setSha256("654c71176b207401445fdd471f5e021f65af50d7361bf828e5b1219c89b977b0");
        ResultModel response = null;
        try {
            response = client.createAttestationWithSha256(payload);
            System.out.println(Utils.objectToJson(response.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSign() throws Exception {
        Map signMap = new TreeMap() {
            {
                put("access_key" , "ceshikey");
                put("tonce", (int) (System.currentTimeMillis() / 1000));
                put("no", "475253949021032448");
            }
        };
        String sign = Utils.sign(client.getPrivateKeyData(), JSON.toJSONString(signMap));
        System.out.println(sign);

    }

    @Test
    public void testCreateAttestationWithFile() throws IOException {
        BaseAttestationPayloadParam payload = new BaseAttestationPayloadParam();
        payload.setTemplate_id("795eac6a8d88401f8926efdef1a95e33");
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
        InputStream inputStream = new FileInputStream("111");

        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "452886513210892289.pdf");
        try {
            response = client.createAttestationWithFile(payload, byteArrayBody);
            System.out.println(Utils.objectToJson(response.getData()));
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void createAttestationWithUrl2() throws ServerException {

        UrlAttestationParam payload = new UrlAttestationParam();
        // 设置保全唯一码
        payload.setUnique_id(randomUniqueId());
        // 设置模板id
        payload.setTemplate_id("4oE5JmY9SJqyieww75rYiW");
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "429006198507104214");
        payload.setIdentities(identities);
        List<PayloadFactoidParam> factoids = new ArrayList<PayloadFactoidParam>();
        PayloadFactoidParam factoid = new PayloadFactoidParam();
        LinkedHashMap<String, String> loanDataMap = new LinkedHashMap<String, String>();
        loanDataMap.put("web_address", "https://jx.tmall.com/?spm=a219t.7664554.1998457203.159.hWZb4X&ali_trackid=2:mm_122806507_911000261_109921750097:1584691132_121_1943880412");
//        loanDataMap.put("web_address", "https://detail.tmall.com/item.htm?spm=a230r.1.14.1.57e28c97rRfHZK&id=568546227960&ns=1&abbucket=2");
        loanDataMap.put("name", "ceshi");
        factoid.setData(loanDataMap);
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("evidence");
        factoids.add(factoid);
        payload.setFactoids(factoids);
//        payload.setUrl("https://www.baidu.com");
        payload.setUrl("https://www.w3school.com.cn");
        payload.setMode(1);
        payload.setEvidenceName("测试取证");
        payload.setEvidenceLabel("测试取证");
        ResultModel response = client.createAttestationWithUrl(payload);
        System.out.println(response.getData());
    }


    public String createAttestationWithUrl() throws ServerException {

        UrlAttestationParam payload = new UrlAttestationParam();
        // 设置保全唯一码
        payload.setUnique_id(randomUniqueId());
        // 设置模板id
        payload.setTemplate_id("4oE5JmY9SJqyieww75rYiW");
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "429006198507104214");
        payload.setIdentities(identities);
        List<PayloadFactoidParam> factoids = new ArrayList<PayloadFactoidParam>();
        PayloadFactoidParam factoid = new PayloadFactoidParam();
        LinkedHashMap<String, String> loanDataMap = new LinkedHashMap<String, String>();
        loanDataMap.put("web_address", "https://jx.tmall.com/?spm=a219t.7664554.1998457203.159.hWZb4X&ali_trackid=2:mm_122806507_911000261_109921750097:1584691132_121_1943880412");
//        loanDataMap.put("web_address", "https://detail.tmall.com/item.htm?spm=a230r.1.14.1.57e28c97rRfHZK&id=568546227960&ns=1&abbucket=2");
        loanDataMap.put("name", "ceshi");
        factoid.setData(loanDataMap);
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("evidence");
        factoids.add(factoid);
        payload.setFactoids(factoids);
//        payload.setUrl("https://www.baidu.com");
        payload.setUrl("https://www.w3school.com.cn");
        payload.setMode(1);
        payload.setEvidenceName("测试取证");
        payload.setEvidenceLabel("测试取证");
        ResultModel response = client.createAttestationWithUrl(payload);
        System.out.println(response.getData());
        return JSON.parseObject(response.getData().toString()).getString("no");
    }

    @Test
    public void createAttestationWithUrl1() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        CountDownLatch countDownLatch = new CountDownLatch(15);
        for (int i = 0; i < 15; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    createAttestationWithUrl();
//                    createAttestationWithUrlConfirm(createAttestationWithUrl());
                    countDownLatch.countDown();
                    System.out.println("线程" + Thread.currentThread().getName() + "执行完成！");
                } catch (Exception e) {
                }
            });
        }
        countDownLatch.await();
    }

//    @Test
    public void createAttestationWithUrlConfirm(String no) throws ServerException {
        UrlAttestationStep2Param payload = new UrlAttestationStep2Param();
        // 设置保全唯一码
        payload.setUnique_id(randomUniqueId());
        // 设置模板id
        payload.setTemplate_id("4g8kLrgrr8AGTXKqUzW1rc");
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "459029550886555648");
        payload.setIdentities(identities);
        payload.setNo(no);

        ResultModel response = client.createAttestationWithUrlConfirm(payload);
        Assert.assertNotNull(response);
    }

    @Test
    public void createAttestationWithUrlConfirm() throws ServerException {
        UrlAttestationStep2Param payload = new UrlAttestationStep2Param();
        // 设置保全唯一码
        payload.setUnique_id(randomUniqueId());
        // 设置模板id
        payload.setTemplate_id("4g8kLrgrr8AGTXKqUzW1rc");
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "459029550886555648");
        payload.setIdentities(identities);
        payload.setNo("479286498051493888");

        ResultModel response = client.createAttestationWithUrlConfirm(payload);
        Assert.assertNotNull(response);
    }

    @Test
    public void downloadImgWithUrlAttestation() throws ServerException {
        UrlAttestationStep2Param payload = new UrlAttestationStep2Param();
        // 设置保全唯一码
        payload.setUnique_id(randomUniqueId());
        // 设置模板id
        payload.setTemplate_id("4g8kLrgrr8AGTXKqUzW1rc");
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "429006198507104214");
        payload.setIdentities(identities);
        payload.setNo("459040791407366145");

        ResultModel response = client.downloadImgWithUrlAttestation(payload);
        Assert.assertNotNull(response);
    }

    @Test
    public void testdownloadFile() {
        try {
            DownloadAttestationInfo response = client.downloadFile("477487559933431809");
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\" + response.getFileName());
            IOUtils.copy(response.getFileInputStream(), fileOutputStream);
            fileOutputStream.close();
//            byteToFile(response.getFileInputStream(),"D:\\test.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void byteToFile(byte[] bytes, String path) {
        try {
            // 根据绝对路径初始化文件
            File localFile = new File(path);
            if (!localFile.exists()) {
                localFile.createNewFile();
            }
            // 输出流
            OutputStream os = new FileOutputStream(localFile);
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void attestationInfo() {

        try {
            ResultModel response = client.attestationInfo("477059053135073280");
            System.out.println(Utils.objectToJson(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAccessUrl() {
        try {
            String accessUrl = client.attestationAccessUrl("479243597808406528");
            System.out.println(Utils.objectToJson(accessUrl));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testcreateProcessToken() {
        ProcessAttestationParam payload = new ProcessAttestationParam();
        payload.setTemplate_id("mqAkuZQwNZbpbrmVTob6Ss");
        payload.setUnique_id(randomUniqueId());
        payload.setEvidenceType("PHONE");
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "15817112383");
        payload.setIdentities(identities);
        List<PayloadFactoidParam> factoids = new ArrayList<PayloadFactoidParam>();
        PayloadFactoidParam factoid = new PayloadFactoidParam();
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("evidence");
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        factoid.setData(map);
        map.put("file_name", "李三");
        map.put("size", "37791");
        factoids.add(factoid);
        payload.setFactoids(factoids);

        ResultModel response = null;
        try {
            response = client.createProcessToken(payload);
        } catch (ServerException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Utils.objectToJson(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        Assert.assertNotNull(response.getData());
    }


    @Test
    public void testgetProcessInfo() {
        ResultModel response = null;
        try {
            response = client.getProcessInfo("480678247831441408");
        } catch (ServerException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Utils.objectToJson(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void teststopProcess() {
        ResultModel response = null;
        try {
            response = client.stopProcess("425043414342438912");
        } catch (ServerException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Utils.objectToJson(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void createMusicAttestation() throws ServerException {

        MusicAttestationParam payload = new MusicAttestationParam();
        // 设置保全唯一码
        payload.setUnique_id(randomUniqueId());
        // 设置模板id
        payload.setTemplate_id("4oE5JmY9SJqyieww75rYiW");
        Map<IdentityTypeEnum, String> identities = new HashMap<IdentityTypeEnum, String>();
        identities.put(IdentityTypeEnum.ID, "429006198507104214");
        payload.setIdentities(identities);
        List<PayloadFactoidParam> factoids = new ArrayList<PayloadFactoidParam>();
        PayloadFactoidParam factoid = new PayloadFactoidParam();
        LinkedHashMap<String, String> loanDataMap = new LinkedHashMap<String, String>();
        loanDataMap.put("web_address", "https://jx.tmall.com/?spm=a219t.7664554.1998457203.159.hWZb4X&ali_trackid=2:mm_122806507_911000261_109921750097:1584691132_121_1943880412");
//        loanDataMap.put("web_address", "https://detail.tmall.com/item.htm?spm=a230r.1.14.1.57e28c97rRfHZK&id=568546227960&ns=1&abbucket=2");
        loanDataMap.put("name", "ceshi");
        factoid.setData(loanDataMap);
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("evidence");
        factoids.add(factoid);
        payload.setFactoids(factoids);
//        payload.setUrl("https://www.baidu.com");
        payload.setUrl("https://y.qq.com/n/yqq/song/001jZ8uJ1OVCvb.html");
        payload.setPlatform("QQ");
        payload.setSinger("周深");
        payload.setSong("有一件美好的事情将要发生");
        payload.setAlbum("QQ");
        ResultModel response = client.createMusicAttestation(payload);
        System.out.println(response.getData());
    }

    @Test
    public void testgetMusicInfo() {
        ResultModel response = null;
        try {
            response = client.getMusicAttestationInfo("609032990105821185");
        } catch (ServerException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Utils.objectToJson(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createECommerceAttestation() throws ServerException {
        ECommerceAttestationParam payload = new ECommerceAttestationParam();
        payload.setUnique_id(randomUniqueId());// 设置保全唯一码
        payload.setTemplate_id("4oE5JmY9SJqyieww75rYiW");// 设置模板id
        Map<IdentityTypeEnum, String> identities = new HashMap<>();
        identities.put(IdentityTypeEnum.ID, "429006198507104214");
        payload.setIdentities(identities);
        List<PayloadFactoidParam> factoids = new ArrayList<PayloadFactoidParam>();
        PayloadFactoidParam factoid = new PayloadFactoidParam();
        LinkedHashMap<String, String> loanDataMap = new LinkedHashMap<String, String>();
        loanDataMap.put("web_address", "xxx");
        loanDataMap.put("name", "ceshi");
        factoid.setData(loanDataMap);
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("evidence");
        factoids.add(factoid);
        payload.setFactoids(factoids);
        payload.setUrl("https://mobile.yangkeduo.com/goods.html?goods_id=192243255532");
        payload.setPlatform("APPAUTOPDD");
        ResultModel response = client.createECommerceAttestation(payload);
        System.out.println(response.getData());
    }

    @Test
    public void getECommerceInfo() {
        ResultModel response = null;
        try {
            response = client.getECommerceAttestationInfo("610113122199805952");
        } catch (ServerException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Utils.objectToJson(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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
