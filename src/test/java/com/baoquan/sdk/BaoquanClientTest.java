package com.baoquan.sdk;

import com.baoquan.sdk.exceptions.ServerException;
import com.baoquan.sdk.pojos.payload.*;
import com.baoquan.sdk.pojos.response.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sbwdlihao on 6/17/16.
 */
public class BaoquanClientTest {

    private static BaoquanClient client;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void initClient() {
        client = new BaoquanClient();
        client.setHost("http://localhost:8080");
        client.setAccessKey("t2GuZhKatpYJuCu4o6b7GZ");
//        client.setHost("https://baoquan.com");
//        client.setAccessKey("kUCJXfceNuCKWeXTaofWXe");
//        client.setHost("http://192.168.3.249:8081");
//        client.setAccessKey("oH64EJTXeQGtewL7jvVzqF");
        try {
//            client.setPemPath(getClass().getClassLoader().getResource("private_key.pem").getPath());
//            client.setPemPath("D:/baoquan/java-sdk/out/test/resources/private_key_bak.pem");
            client.setPemPath("D:/baoquan/java-sdk/out/test/resources/private_key.pem");
//            client.setPemPath("D:/baoquan/java-sdk/src/test/resources/249.pem");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * payload can not be null
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation0() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload can not be null");
        client.createAttestation(null);
    }

    /**
     * unique id can not be null
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation1() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.uniqueId can not be empty");
        client.createAttestation(new CreateAttestationPayload());
    }

    /**
     * payload.templateId can not be empty
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation2() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.templateId can not be empty");
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        client.createAttestation(payload);
    }

    /**
     * payload.identities can not be empty
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation3() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.identities can not be empty");
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
        client.createAttestation(payload);
    }

    /**
     * payload.identities can not be empty
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation4() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.identities can not be empty");
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
        payload.setIdentities(new HashMap<IdentityType, String>());
        client.createAttestation(payload);
    }

    /**
     * payload.factoids can not be empty
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation5() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.factoids can not be empty");
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15857112383");
        payload.setIdentities(identities);
        client.createAttestation(payload);
    }

    /**
     * payload.factoids can not be empty
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation6() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.factoids can not be empty");
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15857112383");
        payload.setIdentities(identities);
        payload.setFactoids(new ArrayList<Factoid>());
        client.createAttestation(payload);
    }

    /**
     * template should be exist
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation7() throws ServerException {
        expectedException.expect(ServerException.class);
        expectedException.expectMessage("模板不存在");
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("2hSWTZ4oqVEJ");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        User user = new User();
        user.setName("张三");
        user.setPhone_number("13234568732");
        user.setRegistered_at("1466674609");
        user.setUsername("tom");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("user");
        factoid.setData(user);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        client.createAttestation(payload);
    }

    /**
     * factoid data should meet with template schema
     * when you edit template schemas on line and set user.phone_number is required
     * you must give a valid phone_number value in user factoid
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation8() throws ServerException {
        expectedException.expect(ServerException.class);
        expectedException.expectMessage("invalid data : user.phone_number required");
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        User user = new User();
        user.setName("张三");
        user.setRegistered_at("1466674609");
        user.setUsername("tom");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("user");
        factoid.setData(user);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        client.createAttestation(payload);
    }

    /**
     * factoid data type should be in template schemas
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation9() throws ServerException {
        expectedException.expect(ServerException.class);
        expectedException.expectMessage("invalid factoid type: product corresponding schema not exist");
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
        payload.setCompleted(false);
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        Product product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        client.createAttestation(payload);
    }

    /**
     * factoid data should meet with template schema
     * when user.phone_number is required but you only upload product
     * you must call addFactoids api to upload user later
     *
     * @throws ServerException
     */
    @Test
    public void testCreateAttestation10() throws ServerException {
        expectedException.expect(ServerException.class);
        expectedException.expectMessage("invalid data : user.phone_number required");
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("5Yhus2mVSMnQRXobRJCYgt");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "12012319800127621X");
        identities.put(IdentityType.MO, "15851212311");
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        Product product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        client.createAttestation(payload);
    }

    @Test
    public void testCreateAttestation11() throws ServerException, IOException {
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setTemplateId("qwBXfBdPXFEgqRK3U2yoVx");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "4201231980012791X");
        identities.put(IdentityType.MO, "1585221311");
        payload.setUniqueId(randomUniqueId());
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        User user = new User();
        user.setName("张三");
        user.setPhone_number("1323456732");
        user.setRegistered_at("1466674609");
        user.setUsername("tom");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("user");
        factoid.setData(user);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        payload.setCompleted(false);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("D:/baoquan/java-sdk/out/test/resources/contract.pdf");
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(new FileInputStream("D:/baoquan/java-sdk/out/test/resources/contract.pdf")), ContentType.DEFAULT_BINARY, "contract.pdf");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
        CreateAttestationResponse response = client.createAttestation(payload, byteStreamBodyMap);
        Assert.assertNotNull(response.getData().getNo());
    }


    public class AttRequest {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileHash() {
            return fileHash;
        }

        public void setFileHash(String fileHash) {
            this.fileHash = fileHash;
        }

        private String orgCode;
        private String fileName;
        private String fileSize;
        private String fileHash;
    }
    @Test
    public void testAddFactoids9() throws ServerException, IOException {
        String ano = "271196C1FEF047A8922F213FAB453238";
        AddFactoidsPayload addFactoidsPayload = new AddFactoidsPayload();
        addFactoidsPayload.setAno(ano);
        AttRequest att = new AttRequest();
        att.setName("test name");

        att.setOrgCode("1111");
        att.setFileName("test.pdf");
        att.setFileSize("1024");
        att.setFileHash(String.valueOf("test".hashCode()));
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        factoid.setUnique_id(UUID.randomUUID().toString());
        factoid.setType("att");
        factoid.setData(att);
        factoids.add(factoid);
        addFactoidsPayload.setFactoids(factoids);
        AddFactoidsResponse addFactoidsResponse = client.addFactoids(addFactoidsPayload);
        Assert.assertNotNull(addFactoidsResponse.getRequest_id());
        Assert.assertNotNull(addFactoidsResponse.getData());
        Assert.assertTrue(addFactoidsResponse.getData().isSuccess());
    }


    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testCreateAttestation12() throws ServerException, IOException {
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        payload.setUniqueId(randomUniqueId());
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        User user = new User();
        user.setName("张三");
        user.setPhone_number("13234568732");
        user.setRegistered_at("1466674609");
        user.setUsername("tom");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("user");
        factoid.setData(user);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contract.pdf");
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "contract.pdf");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
        CreateAttestationResponse response = client.createAttestation(payload, byteStreamBodyMap);
        Assert.assertNotNull(response.getData().getNo());

        CreateAttestationResponse response1 = client.createAttestation(payload, byteStreamBodyMap);
        Assert.assertEquals(response1.getData().getNo(), response.getData().getNo());
    }

    @Test
    public void testCreateAttestationWithSha256() throws ServerException {
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setTemplateId("filehash");
        payload.setUniqueId(randomUniqueId());
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.MO, "15852212311");
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("file");
        Map<String, String> map = new HashMap<String, String>();
        factoid.setData(map);
        map.put("owner_name", "李三");
        map.put("owner_id", "330124199501017791");
        factoids.add(factoid);
        payload.setFactoids(factoids);
        CreateAttestationResponse response = client.createAttestationWithSha256(payload, "654c71176b207401445fdd471f5e023f65af50d7361bf828e5b1c19c89b977b0");
        Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * payload can not be null
     *
     * @throws ServerException
     */
    @Test
    public void testAddFactoids0() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload can not be null");
        client.addFactoids(null);
    }

    /**
     * payload.ano can not be empty
     *
     * @throws ServerException
     */
    @Test
    public void testAddFactoids1() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.ano can not be empty");
        client.addFactoids(new AddFactoidsPayload());
    }

    /**
     * payload.factoids can not be empty
     *
     * @throws ServerException
     */
    @Test
    public void testAddFactoids2() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.factoids can not be empty");
        AddFactoidsPayload payload = new AddFactoidsPayload();
        payload.setAno("D58FFFD28A8949969611883B6EABA148");
        client.addFactoids(payload);
    }

    /**
     * payload.factoids can not be empty
     *
     * @throws ServerException
     */
    @Test
    public void testAddFactoids3() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.factoids can not be empty");
        AddFactoidsPayload payload = new AddFactoidsPayload();
        payload.setAno("D58FFFD28A8949969611883B6EABA148");
        payload.setFactoids(new ArrayList<Factoid>());
        client.addFactoids(payload);
    }

    /**
     * attestation must be exist
     *
     * @throws ServerException
     */
    @Test
    public void testAddFactoids4() throws ServerException {
        expectedException.expect(ServerException.class);
        expectedException.expectMessage("保全不存在");
        AddFactoidsPayload payload = new AddFactoidsPayload();
        payload.setAno("D58FFFD28A8949");
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        Product product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        client.addFactoids(payload);
    }

    /**
     * attestation completed and can not add factoids
     *
     * @throws ServerException
     */
    @Test
    public void testAddFactoids5() throws ServerException {
        expectedException.expect(ServerException.class);
        expectedException.expectMessage("保全已完成,不能继续追加陈述");
        AddFactoidsPayload payload = new AddFactoidsPayload();
        payload.setAno("4E6457A5A9B94FBFB64E0D08BDFA2BD4");
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        Product product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        client.addFactoids(payload);
    }

    /**
     * when complete attestation, factoids should meet with schemas
     *
     * @throws ServerException
     */
    @Test
    public void testAddFactoids6() throws ServerException {
        CreateAttestationPayload createAttestationPayload = new CreateAttestationPayload();
        createAttestationPayload.setUniqueId(randomUniqueId());
        createAttestationPayload.setTemplateId("5Yhus2mVSMnQRXobRJCYgt");
        createAttestationPayload.setCompleted(false);
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        createAttestationPayload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        Product product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        createAttestationPayload.setFactoids(factoids);
        CreateAttestationResponse response = client.createAttestation(createAttestationPayload);
        Assert.assertNotNull(response.getRequest_id());
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getNo());

        String ano = response.getData().getNo();
        AddFactoidsPayload addFactoidsPayload = new AddFactoidsPayload();
        addFactoidsPayload.setAno(ano);
        factoids = new ArrayList<Factoid>();
        factoid = new Factoid();
        product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        addFactoidsPayload.setFactoids(factoids);
        expectedException.expect(ServerException.class);
        expectedException.expectMessage("invalid data : user.phone_number required");
        client.addFactoids(addFactoidsPayload);
    }

    /**
     * same unique id will return success
     *
     * @throws ServerException
     */
    @Test
    public void testAddFactoids7() throws ServerException {
        CreateAttestationPayload createAttestationPayload = new CreateAttestationPayload();
        createAttestationPayload.setUniqueId(randomUniqueId());
        createAttestationPayload.setTemplateId("5Yhus2mVSMnQRXobRJCYgt");
        createAttestationPayload.setCompleted(false);
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        createAttestationPayload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        Product product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        String fuid = randomUniqueId();
        factoid.setUnique_id(fuid);
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        createAttestationPayload.setFactoids(factoids);
        CreateAttestationResponse createAttestationResponse = client.createAttestation(createAttestationPayload);
        Assert.assertNotNull(createAttestationResponse.getRequest_id());
        Assert.assertNotNull(createAttestationResponse.getData());
        Assert.assertNotNull(createAttestationResponse.getData().getNo());

        String ano = createAttestationResponse.getData().getNo();
        AddFactoidsPayload addFactoidsPayload = new AddFactoidsPayload();
        addFactoidsPayload.setAno(ano);
        addFactoidsPayload.setCompleted(false);
        factoids = new ArrayList<Factoid>();
        factoid = new Factoid();
        product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        factoid.setUnique_id(fuid);
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        addFactoidsPayload.setFactoids(factoids);
        AddFactoidsResponse addFactoidsResponse = client.addFactoids(addFactoidsPayload);
        Assert.assertNotNull(addFactoidsResponse.getRequest_id());
        Assert.assertNotNull(addFactoidsResponse.getData());
        Assert.assertTrue(addFactoidsResponse.getData().isSuccess());
    }

    /**
     * create attestation and then add factoid
     *
     * @throws ServerException
     */
    @Test
    public void testAddFactoids8() throws ServerException, IOException {
        CreateAttestationPayload createAttestationPayload = new CreateAttestationPayload();
        createAttestationPayload.setUniqueId(randomUniqueId());
        createAttestationPayload.setTemplateId("jEe8cUBHKsjSoBfUbH4f9n");
        createAttestationPayload.setCompleted(false);
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        createAttestationPayload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        Product product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        createAttestationPayload.setFactoids(factoids);
        CreateAttestationResponse createAttestationResponse = client.createAttestation(createAttestationPayload);
        Assert.assertNotNull(createAttestationResponse.getRequest_id());
        Assert.assertNotNull(createAttestationResponse.getData());
        Assert.assertNotNull(createAttestationResponse.getData().getNo());

        String ano = createAttestationResponse.getData().getNo();
        AddFactoidsPayload addFactoidsPayload = new AddFactoidsPayload();
        addFactoidsPayload.setAno(ano);
        factoids = new ArrayList<Factoid>();
        factoid = new Factoid();
        User user = new User();
        user.setName("张三");
        user.setRegistered_at("1466674609");
        user.setUsername("tom");
        user.setPhone_number("13452345987");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("user");
        factoid.setData(user);
        factoids.add(factoid);
        addFactoidsPayload.setFactoids(factoids);
        AddFactoidsResponse addFactoidsResponse = client.addFactoids(addFactoidsPayload);
        Assert.assertNotNull(addFactoidsResponse.getRequest_id());
        Assert.assertNotNull(addFactoidsResponse.getData());
        Assert.assertTrue(addFactoidsResponse.getData().isSuccess());
    }




    @Test
    public void testAddFactoids11() throws ServerException, IOException {
        AddFactoidsPayload addFactoidsPayload = new AddFactoidsPayload();
        addFactoidsPayload.setAno("D7135D44E1DD43628CE7EBAF77054231");
        addFactoidsPayload.setCompleted(true);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        User user = new User();
        user.setName("王五");
        user.setPhone_number("13234568732");
        user.setRegistered_at("1466674609");
        user.setUsername("tom");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("user");
        factoid.setData(user);
        factoids.add(factoid);
        addFactoidsPayload.setFactoids(factoids);
        AddFactoidsResponse addFactoidsResponse = client.addFactoids(addFactoidsPayload);
        Assert.assertNotNull(addFactoidsResponse.getRequest_id());
        Assert.assertNotNull(addFactoidsResponse.getData());
        Assert.assertTrue(addFactoidsResponse.getData().isSuccess());
    }

    /**
     * payload can not be null
     *
     * @throws ServerException
     */
    @Test
    public void testApplyCa0() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload can not be null");
        client.applyCa(null, null);
    }

    /**
     * payload.type can not be null
     *
     * @throws ServerException
     */
    @Test
    public void testApplyCa1() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.type can not be null");
        client.applyCa(new ApplyCaPayload(), null);
    }

    /**
     * payload.linkName can not be empty
     *
     * @throws ServerException
     */
    @Test
    public void testApplyCa2() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.linkName can not be empty");
        ApplyCaPayload payload = new ApplyCaPayload();
        payload.setType(CaType.PERSONAL);
        client.applyCa(payload, null);
    }

    /**
     * payload.name can not be empty when type is enterprise
     *
     * @throws ServerException
     */
    @Test
    public void testApplyCa3() throws ServerException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("payload.name can not be empty");
        ApplyCaPayload payload = new ApplyCaPayload();
        payload.setType(CaType.ENTERPRISE);
        client.applyCa(payload, null);
    }

    /**
     * apply personal ca
     *
     * @throws ServerException
     */
    @Test
    public void testApplyCa4() throws ServerException {
        ApplyCaPayload payload = new ApplyCaPayload();
        payload.setType(CaType.PERSONAL);
        payload.setLinkName("张三");
        payload.setLinkIdCard(randomIDCard());
        payload.setLinkPhone(randomPhone());
        payload.setLinkEmail(randomPhone() + "@qq.com");
        ApplyCaResponse response = client.applyCa(payload, null);
        Assert.assertNotNull(response.getRequest_id());
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * apply enterprise ca
     *
     * @throws ServerException
     */
    @Test
    public void testApplyCa5() throws ServerException, IOException {
        ApplyCaPayload payload = new ApplyCaPayload();
        payload.setType(CaType.ENTERPRISE);
        payload.setName("浙金网");
        payload.setIcCode("91330105311263043J");
        payload.setOrgCode("311263043");
        payload.setTaxCode("330105311263043");
        payload.setLinkName("张三");
        payload.setLinkIdCard(randomIDCard());
        payload.setLinkPhone(randomPhone());
        payload.setLinkEmail(randomPhone() + "@qq.com");
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("seal.png");
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "seal.png");
        ApplyCaResponse response = client.applyCa(payload, byteArrayBody);
        Assert.assertNotNull(response.getRequest_id());
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * add one factoid with one attachment that need to sign
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSign0() throws ServerException, IOException {
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        User user = new User();
        user.setName("张三");
        user.setRegistered_at("1466674609");
        user.setUsername("tom");
        user.setPhone_number("13452345987");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("user");
        factoid.setData(user);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        Map<String, Map<String, Map<String, List<String>>>> iMap = new HashMap<String, Map<String, Map<String, List<String>>>>();
        Map<String, Map<String, List<String>>> jMap = new HashMap<String, Map<String, List<String>>>();
        Map<String, List<String>> signs = new HashMap<String, List<String>>();
        signs.put("F98F99A554E944B6996882E8A68C60B2", Collections.singletonList("甲方（签章）"));
        signs.put("0A68783469E04CAC95ADEAE995A92E65", Collections.singletonList("乙方（签章）"));
        jMap.put("0", signs);
        iMap.put("0", jMap);
        payload.setSigns(iMap);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contract.pdf");
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "contract.pdf");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
        CreateAttestationResponse response = client.createAttestation(payload, byteStreamBodyMap);
        Assert.assertNotNull(response.getRequest_id());
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * add one factoid with two attachments and one of them need to sign
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSign1() throws ServerException, IOException {
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        User user = new User();
        user.setName("张三");
        user.setRegistered_at("1466674609");
        user.setUsername("tom");
        user.setPhone_number("13452345987");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("user");
        factoid.setData(user);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        Map<String, Map<String, Map<String, List<String>>>> iMap = new HashMap<String, Map<String, Map<String, List<String>>>>();
        Map<String, Map<String, List<String>>> jMap = new HashMap<String, Map<String, List<String>>>();
        Map<String, List<String>> signs = new HashMap<String, List<String>>();
        signs.put("F98F99A554E944B6996882E8A68C60B2", Collections.singletonList("甲方（签章）"));
        signs.put("0A68783469E04CAC95ADEAE995A92E65", Collections.singletonList("乙方（签章）"));
        jMap.put("0", signs);
        iMap.put("0", jMap);
        payload.setSigns(iMap);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contract.pdf");
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "contract.pdf");
        InputStream inputStream1 = getClass().getClassLoader().getResourceAsStream("seal.png");
        ByteArrayBody byteArrayBody1 = new ByteArrayBody(IOUtils.toByteArray(inputStream1), ContentType.DEFAULT_BINARY, "seal.png");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        byteStreamBodyMap.put("0", Arrays.asList(byteArrayBody, byteArrayBody1));
        CreateAttestationResponse response = client.createAttestation(payload, byteStreamBodyMap);
        Assert.assertNotNull(response.getRequest_id());
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * add two factoids with attachments and one of the factoids has attachment need to sign
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSign2() throws ServerException, IOException {
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("5Yhus2mVSMnQRXobRJCYgt");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        // add product
        Factoid factoid = new Factoid();
        Product product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        // add user
        factoid = new Factoid();
        User user = new User();
        user.setName("张三");
        user.setRegistered_at("1466674609");
        user.setUsername("tom");
        user.setPhone_number("13452345987");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("user");
        factoid.setData(user);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        Map<String, Map<String, Map<String, List<String>>>> iMap = new HashMap<String, Map<String, Map<String, List<String>>>>();
        Map<String, Map<String, List<String>>> jMap = new HashMap<String, Map<String, List<String>>>();
        Map<String, List<String>> signs = new HashMap<String, List<String>>();
        signs.put("F98F99A554E944B6996882E8A68C60B2", Collections.singletonList("甲方（签章）"));
        signs.put("0A68783469E04CAC95ADEAE995A92E65", Collections.singletonList("乙方（签章）"));
        jMap.put("1", signs);
        iMap.put("1", jMap);
        payload.setSigns(iMap);
        InputStream inputStream0 = getClass().getClassLoader().getResourceAsStream("seal.png");
        ByteArrayBody byteArrayBody0 = new ByteArrayBody(IOUtils.toByteArray(inputStream0), ContentType.DEFAULT_BINARY, "seal.png");
        InputStream inputStream1 = getClass().getClassLoader().getResourceAsStream("seal.png");
        ByteArrayBody byteArrayBody1 = new ByteArrayBody(IOUtils.toByteArray(inputStream1), ContentType.DEFAULT_BINARY, "seal.png");
        InputStream inputStream2 = getClass().getClassLoader().getResourceAsStream("contract.pdf");
        ByteArrayBody byteArrayBody2 = new ByteArrayBody(IOUtils.toByteArray(inputStream2), ContentType.DEFAULT_BINARY, "contract.pdf");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody0));
        byteStreamBodyMap.put("1", Arrays.asList(byteArrayBody1, byteArrayBody2));
        CreateAttestationResponse response = client.createAttestation(payload, byteStreamBodyMap);
        Assert.assertNotNull(response.getRequest_id());
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * add two factoids with attachments and one of the factoids has attachment need to sign
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSign3() throws ServerException, IOException {
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("5Yhus2mVSMnQRXobRJCYgt");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15852212311");
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        // add product
        Factoid factoid = new Factoid();
        Product product = new Product();
        product.setName("浙金网");
        product.setDescription("p2g理财平台");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("product");
        factoid.setData(product);
        factoids.add(factoid);
        // add user
        factoid = new Factoid();
        User user = new User();
        user.setName("张三");
        user.setRegistered_at("1466674609");
        user.setUsername("tom");
        user.setPhone_number("13452345987");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("user");
        factoid.setData(user);
        factoids.add(factoid);
        payload.setFactoids(factoids);
        Map<String, Map<String, Map<String, List<String>>>> iMap = new HashMap<String, Map<String, Map<String, List<String>>>>();
        Map<String, Map<String, List<String>>> jMap = new HashMap<String, Map<String, List<String>>>();
        Map<String, List<String>> signs = new HashMap<String, List<String>>();
        signs.put("F98F99A554E944B6996882E8A68C60B2", Collections.singletonList("甲方（签章）"));
        signs.put("0A68783469E04CAC95ADEAE995A92E65", Collections.singletonList("乙方（签章）"));
        jMap.put("1", signs);
        iMap.put("1", jMap);
        payload.setSigns(iMap);
        InputStream inputStream1 = getClass().getClassLoader().getResourceAsStream("seal.png");
        ByteArrayBody byteArrayBody1 = new ByteArrayBody(IOUtils.toByteArray(inputStream1), ContentType.DEFAULT_BINARY, "seal.png");
        InputStream inputStream2 = getClass().getClassLoader().getResourceAsStream("contract.pdf");
        ByteArrayBody byteArrayBody2 = new ByteArrayBody(IOUtils.toByteArray(inputStream2), ContentType.DEFAULT_BINARY, "contract.pdf");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        byteStreamBodyMap.put("1", Arrays.asList(byteArrayBody1, byteArrayBody2));
        CreateAttestationResponse response = client.createAttestation(payload, byteStreamBodyMap);
        Assert.assertNotNull(response.getRequest_id());
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getNo());
    }

    @Test
    public void testGetAttestation0() throws ServerException {
        expectedException.expect(ServerException.class);
        expectedException.expectMessage("保全不存在");
        client.getAttestation("DB0C8DB14E3C44", null);
    }

    @Test
    public void testGetAttestation1() throws ServerException {
        GetAttestationResponse response = client.getAttestation("0E9A26E72C51453BB8F8A0C4FEE9BE3F", null);
        Assert.assertNotNull(response.getRequest_id());
        Assert.assertNotNull(response.getData());
        Assert.assertEquals("0E9A26E72C51453BB8F8A0C4FEE9BE3F", response.getData().getNo());
    }

    @Test
    public void testGetAttestation2() throws ServerException {
        GetAttestationResponse response = client.getAttestation("0E9A26E72C51453BB8F8A0C4FEE9BE3F", new ArrayList<String>());
        Assert.assertNotNull(response.getRequest_id());
        Assert.assertNotNull(response.getData());
        Assert.assertEquals("0E9A26E72C51453BB8F8A0C4FEE9BE3F", response.getData().getNo());
        Assert.assertNull(response.getData().getIdentities());
        Assert.assertNull(response.getData().getFactoids());
        Assert.assertNull(response.getData().getAttachments());
    }

    @Test
    public void testGetAttestation3() throws ServerException {
        GetAttestationResponse response = client.getAttestation("0E9A26E72C51453BB8F8A0C4FEE9BE3F", Collections.singletonList("factoids"));
        Assert.assertNotNull(response.getRequest_id());
        Assert.assertNotNull(response.getData());
        Assert.assertEquals("0E9A26E72C51453BB8F8A0C4FEE9BE3F", response.getData().getNo());
        Assert.assertNull(response.getData().getIdentities());
        Assert.assertNotNull(response.getData().getFactoids());
        Assert.assertNull(response.getData().getAttachments());
    }

    @Test
    public void testDownloadAttestation0() throws ServerException, IOException {
        DownloadFile downloadFile = client.downloadAttestation("0000990A7473421C9BCF44566F164E4A");
        Assert.assertNotNull(downloadFile);
        Assert.assertNotNull(downloadFile.getFileName());
        Assert.assertNotNull(downloadFile.getFile());

        FileOutputStream fileOutputStream = new FileOutputStream(downloadFile.getFileName());
        IOUtils.copy(downloadFile.getFile(), fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void testDownloadContract() throws ServerException, IOException {
        DownloadFile downloadFile = client.downloadContract("jVef7CWtiFTvGRZ9ZG6ndD");
        Assert.assertNotNull(downloadFile);
        Assert.assertNotNull(downloadFile.getFileName());
        Assert.assertNotNull(downloadFile.getFile());

        FileOutputStream fileOutputStream = new FileOutputStream(downloadFile.getFileName());
        IOUtils.copy(downloadFile.getFile(), fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void testUserKyc() throws ServerException {
        UserKycResponse response = client.userKyc("19888888881", "李涛", "210682199505041375");
        String userId = response.getData().getUserId();
//        System.out.println(userId);
//        Assert.assertNotNull(response.getData().getUserId());

    }

    @Test
    public void testAttestationUrl() throws ServerException {
        String accessUrl = client.attestationAccessUrl("35F79C28AC244202B0AA0A4E3E316AEC");
        System.out.println(accessUrl);
    }

    @Test
    public void testSignContract() throws ServerException {
        Map<String, String> identitiesMap = new HashMap<String, String>();
        List<PayloadFactoid> list = new ArrayList<PayloadFactoid>();
        PayloadFactoid payloadFactoid = new PayloadFactoid();
        LinkedHashMap<String , Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("userTruename","张三");
        linkedHashMap.put("address", "hangzhou");
        payloadFactoid.setType("product");
        payloadFactoid.setData(linkedHashMap);
        list.add(payloadFactoid);
        identitiesMap.put("MO", "15611111111");
        identitiesMap.put("ID", "430426198401361452");
        client.signContract("drdXSskmM3CmYM3HTvLWWJ", "18368729972", "6387", "DONE", "4", "400", "550","_priv_template_2", identitiesMap, list,false,"","enterprise","");
    }

    @Test
    public void testSetContractGroupStatus() throws ServerException {
        Map<String, String> identitiesMap = new HashMap<String, String>();
        List<PayloadFactoid> list = new ArrayList<PayloadFactoid>();
        PayloadFactoid payloadFactoid = new PayloadFactoid();
        LinkedHashMap<String , Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("userTruename","张三");
        linkedHashMap.put("address", "hangzhou");
        payloadFactoid.setType("product");
        payloadFactoid.setData(linkedHashMap);
        list.add(payloadFactoid);
        identitiesMap.put("MO","15611111111");
        identitiesMap.put("ID", "430426198401311452");
        client.setContractGroupStatus("vpzMC8JXdpFgoop9wnMguR", "13145205611", "4520", "DONE", "4", "400", "550","_priv_template_2", identitiesMap, list,false,"","personal");
    }

    @Test
    public void testSendVerifyCode() throws ServerException {
        client.sendVerifyCode("drdXSskmM3CmYM3HTvLWWJ", "18368729972");
    }

    @Test
    public void testSendVerifyCodeForGroup() throws ServerException {
        client.sendVerifyCodeForGroup("kRcDGVqwxrKmjG1oBjH5BN", "18272161340");
    }

    @Test
    public void testListSignature() throws ServerException {
        ListSignatureResponse list = client.listSignature();
    }


//    @Test
//    public void testUploadDoneContract1() throws ServerException, IOException {
//        ContractPayload payload = new ContractPayload();
//        payload.setTemplateId("5e4G1Kdr8ThS71HBdW64A");
//        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
//        identities.put(IdentityType.ID, "42012319800127691X");
//        identities.put(IdentityType.MO, "15811111111");
//        payload.setUniqueId(randomUniqueId());
//        payload.setIdentities(identities);
//        List<Factoid> factoids = new ArrayList<Factoid>();
//        Factoid factoid = new Factoid();
//        User user = new User();
//        user.setName("张三");
//        user.setPhone_number("15811111111");
//        user.setRegistered_at("1466674609");
//        user.setUsername("tom");
//        factoid.setUnique_id(randomUniqueId());
//        factoid.setType("user");
//        factoid.setData(user);
//        factoids.add(factoid);
//        payload.setFactoids(factoids);
//
//        payload.setEnd_at(new Date());
//        // payload.setName("xxx合同");
//        payload.setName("contract.pdf");
////        payload.setOrganization_id("qwdwcscsdafqwq");
//        payload.setRemark("zheshixxxxxxxxxxxxxxx");
//        payload.setStatus("DONE");
//        payload.setTitle("ssss合同");
//
//        List<String> usePhones = new ArrayList();
//        usePhones.add("15811111111");
//        payload.setUserPhones(usePhones);
//
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contract.pdf");
//        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "contract.pdf");
//        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
//        byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
//        CreateAttestationResponse response = client.uploadContract(payload, byteArrayBody);
//        Assert.assertNotNull(response.getData().getNo());
//    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testUploadContract() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contract.pdf"); //  contract.pdf
        InputStream inputStream = new FileInputStream("D:/baoquan/java-sdk/out/test/resources/contract.pdf") ;//  contract.pdf
        System.out.println(getClass().getClassLoader());
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "contract.pdf");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
        UploadContractResponse u = client.uploadContract(payload, byteStreamBodyMap);
        System.out.println(u.getContractId());
        //Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testCreateGroup() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contract.pdf");
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "contract.pdf");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
        CreateGroupResponse u = client.createGroup(payload, byteStreamBodyMap);
        System.out.println(u.getGroupId());
        //Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testUploadContract1() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contract.pdf");
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "contract.pdf");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        //  byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
        UploadContractResponse u = client.uploadContract(payload, byteStreamBodyMap);
        //Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSetContractuDetail() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
//        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.add(Calendar.YEAR, +1);
        date = calendar.getTime();
        System.out.println(date);
        payload.setEnd_at(date);

        payload.setRemark("这是合同");

        payload.setTitle("aaaa合同");

        payload.setContract_id("drdXSskmM3CmYM3HTvLWWJ");

        List<String> usePhones = new ArrayList();
//        usePhones.add("18106500602");
//        usePhones.add("18551824340");
//        usePhones.add("18368729972");
//        usePhones.add("15822222222");
////        usePhones.add("15833333333");
        usePhones.add("18272161340");
        usePhones.add("18368729972");

        payload.setUserPhones(usePhones);

        payload.setYourself(false);
        ResultResponse u  = client.setContractDetail(payload);
        //Assert.assertNotNull(response.getData().getNo());
    }

    @Test
    public void testSetContractGroupDetail() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
//        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.add(Calendar.YEAR, +1);
        date = calendar.getTime();
        System.out.println(date);
        payload.setEnd_at(date);

        payload.setRemark("zhes");

        payload.setTitle("ss添加2");

//        payload.setContract_id("vcVuhR2e1odTShZnJug7cg");

        payload.setGroup_id("kRcDGVqwxrKmjG1oBjH5BN");

        List<String> usePhones = new ArrayList();
        usePhones.add("18322222222");
        payload.setUserPhones(usePhones);

        payload.setYourself(true);
        client.setContractGroupDetail(payload);
        //Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSetContractuDetail1() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();

        payload.setRemark("");

        payload.setTitle("ssss合同");

        payload.setContract_id("bvN6zPZJMd9ZGALGphPRNF");

        List<String> usePhones = new ArrayList();
        usePhones.add("15844444444");
        payload.setUserPhones(usePhones);


        client.setContractDetail(payload);
        //Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSetContractuDetail2() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();

        payload.setEnd_at(new Date());

        payload.setTitle("ssss合同");

        payload.setContract_id("bvN6zPZJMd9ZGALGphPRNF");

        List<String> usePhones = new ArrayList();
        usePhones.add("15844444444");
        payload.setUserPhones(usePhones);


        client.setContractDetail(payload);
        //Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSetContractuDetail3() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();

        payload.setEnd_at(new Date());

        payload.setRemark("awef");


        payload.setContract_id("bvN6zPZJMd9ZGALGphPRNF");

        List<String> usePhones = new ArrayList();
        usePhones.add("15844444444");
        payload.setUserPhones(usePhones);


        client.setContractDetail(payload);
        //Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSetContractuDetail4() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();

        payload.setEnd_at(new Date());

        payload.setRemark("ewfw");

        payload.setTitle("ssss合同");


        List<String> usePhones = new ArrayList();
        usePhones.add("15844444444");
        payload.setUserPhones(usePhones);


        client.setContractDetail(payload);
        //Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSetContractuDetail5() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();

        payload.setEnd_at(new Date());

        payload.setRemark("dfvawsde");

        payload.setTitle("ssss合同");

        payload.setContract_id("bvN6zPZJMd9ZGALGphPRNF");

        List<String> usePhones = new ArrayList();
        payload.setUserPhones(usePhones);


        client.setContractDetail(payload);
        //Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testSetContractuDetail6() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();

        payload.setEnd_at(new Date());

        payload.setRemark("sas");

        payload.setTitle("ssss合同");

        payload.setContract_id("bvN6zPZJMd9ZGALGphPRNF");

        List<String> usePhones = new ArrayList();
        usePhones.add("15333344");
        payload.setUserPhones(usePhones);


        client.setContractDetail(payload);
        //Assert.assertNotNull(response.getData().getNo());
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testQueryList() throws ServerException, IOException {
        ContractListPayload payload = new ContractListPayload();
        payload.setStatus("DONE");
//        payload.setKeyWord("张");
//        payload.setStart(new Date());
//        payload.setEnd(new Date());
        client.queryList(payload);
    }


    @Test
    public void testgetDetail() throws ServerException {
        client.getDetail("01");
    }

    @Test
    public void testSetContractuDetail7() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();

        payload.setEnd_at(new Date());

        payload.setRemark("sas");

        payload.setTitle("ssss合同");

        payload.setContract_id("bvN6zPZJMd9ZGALRNF");

        List<String> usePhones = new ArrayList();
        usePhones.add("15844444444");
        usePhones.add("18811111111");
        payload.setUserPhones(usePhones);


        client.setContractDetail(payload);
        //Assert.assertNotNull(response.getData().getNo());
    }


    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testUploadSignature() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("seal.png");
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "seal.png");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
        UploadSignatureResponse u = client.uploadSignature(payload, byteStreamBodyMap);
        //  Assert.assertNotNull(response);
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testUploadSignature1() throws ServerException, IOException {
        ContractPayload payload = new ContractPayload();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("seal.png");
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "seal.png");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        //byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
        UploadSignatureResponse u = client.uploadSignature(payload, byteStreamBodyMap);
        //  Assert.assertNotNull(response);
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void SetSignatureDefaultId() throws ServerException, IOException {
        SignaturePayload payload = new SignaturePayload();
        payload.setSignature_id("cey4FBLpqbsUNaLp3SENdp");
        client.setSignatureDefaultId(payload);
        //  Assert.assertNotNull(response);
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void SetSignatureDefaultId1() throws ServerException, IOException {
        SignaturePayload payload = new SignaturePayload();
        payload.setSignature_id("e12UdCis3omk991");
        client.setSignatureDefaultId(payload);
        //  Assert.assertNotNull(response);
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void SetSignatureDefaultId2() throws ServerException, IOException {
        SignaturePayload payload = new SignaturePayload();
        payload.setSignature_id("");
        client.setSignatureDefaultId(payload);
        //  Assert.assertNotNull(response);
    }


    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void deleteSignature() throws ServerException, IOException {
        SignaturePayload payload = new SignaturePayload();
        payload.setSignature_id("nePd7d55vb9q9AJLmacVgt");
        client.deleteSignature(payload);
        //  Assert.assertNotNull(response);
    }

    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void deleteSignature1() throws ServerException, IOException {
        SignaturePayload payload = new SignaturePayload();
        payload.setSignature_id("");
        client.deleteSignature(payload);
        //  Assert.assertNotNull(response);
    }


    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void deleteSignature2() throws ServerException, IOException {
        SignaturePayload payload = new SignaturePayload();
        payload.setSignature_id("nePd7macVgt");
        client.deleteSignature(payload);
        //  Assert.assertNotNull(response);
    }


    /**
     * create attestation with the same unique id will return the same attestation no
     *
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void signatureList() throws ServerException, IOException {
        SignaturePayload payload = new SignaturePayload();
        client.signatureList(payload);
        //  Assert.assertNotNull(response);
    }

    /**
     * @throws ServerException
     * @throws IOException
     */
    @Test
    public void testKycEnterprise() throws ServerException, IOException {
        KycEnterprisePayload payload = new KycEnterprisePayload();
        payload.setAccountName("潇潇公司");
        payload.setBank("中国银行");
        payload.setBankAccount("1111111111111");
        payload.setName("这是我的新公");
        payload.setOrgcode("1234157");
        payload.setPhone("18312455527");
//        InputStream businessInputStream = getClass().getClassLoader().getResourceAsStream("seal.png");
        InputStream businessInputStream = new FileInputStream("D:/baoquan/java-sdk/out/test/resources/seal.png");
        ByteArrayBody businessFile = new ByteArrayBody(IOUtils.toByteArray(businessInputStream), ContentType.DEFAULT_BINARY, "seal.png");
        kycEnterpriseResponse response = client.kycEnterprise(payload, businessFile);
    }

    @Test
    public void testcreateAttestationWithUrl() throws ServerException, IOException {
        CreateAttestationPayload payload = new CreateAttestationPayload();
        payload.setUniqueId(randomUniqueId());
        payload.setTemplateId("6J6yeT7rhLjTUUuxS6zMwD");
//        payload.setTemplateId("ctQrUossB7cB4kfPX7h7Wu");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "4201231980012761X");
//        identities.put(IdentityType.USCID, "91330101341890873G");
        payload.setIdentities(identities);
        List<Factoid> factoids = new ArrayList<Factoid>();
        Factoid factoid = new Factoid();
        HashMap<String, String> factoidData = new HashMap<String, String>();

        factoidData.put("url" , "https://mail.qq.com");
        factoid.setUnique_id(randomUniqueId());
        factoid.setType("website");
        factoid.setData(factoidData);
        factoids.add(factoid);
        payload.setFactoids(factoids);


//        String url = "http://news.21cn.com/caiji/roll1/zhnb/2017/0628/12/32422879_all.shtml";
        String url = "https://mail.qq.com";
        CreateAttestationResponse response = client.createAttestationWithUrl(payload, url);
        Assert.assertNotNull(response.getData().getNo());
    }


    @Test
    public void testSendAuthorizationVerifyCode() throws ServerException {
        client.sendAuthorizationVerifyCode( "15811111111");
    }

    @Test
    public void testauthorized() throws ServerException {
        client.authorized( "15811111111","7333");
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
