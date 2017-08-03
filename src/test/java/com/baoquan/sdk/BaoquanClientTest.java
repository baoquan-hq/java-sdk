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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        client.setAccessKey("fsBswNzfECKZH9aWyh47fc");
        try {
            client.setPemPath(getClass().getClassLoader().getResource("private_key.pem").getPath());
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
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15857112383");
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
        payload.setTemplateId("5e4G1Kdr8ThS71HBdW64A");
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.MO, "15857112383");
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
        createAttestationPayload.setTemplateId("5Yhus2mVSMnQRXobRJCYgt");
        createAttestationPayload.setCompleted(false);
        Map<IdentityType, String> identities = new HashMap<IdentityType, String>();
        identities.put(IdentityType.ID, "42012319800127691X");
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.MO, "15857112383");
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
        identities.put(IdentityType.MO, "15857112383");
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
        DownloadFile downloadFile = client.downloadAttestation("220271AF2CB94E67AB36E6D9341AB053");
        Assert.assertNotNull(downloadFile);
        Assert.assertNotNull(downloadFile.getFileName());
        Assert.assertNotNull(downloadFile.getFile());

        FileOutputStream fileOutputStream = new FileOutputStream(downloadFile.getFileName());
        IOUtils.copy(downloadFile.getFile(), fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void testUserKyc() throws ServerException {
        UserKycResponse response = client.userKyc("15822222224", "用户一", "42012319800127691X");
        String userId = response.getData().getUserId();
        System.out.println(userId);
        Assert.assertNotNull(response.getData().getUserId());

    }

    @Test
    public void testAttestationUrl() throws ServerException {
        String accessUrl = client.attestationAccessUrl("35F79C28AC244202B0AA0A4E3E316AEC");
        System.out.println(accessUrl);
    }

    @Test
    public void testSignContract() throws ServerException {
        client.signContract("iUuYWkVVSfaSbY3nKg1dL4", "15822222222", "7373", "REJECT", "4", "400", "550");
    }

    @Test
    public void testSendVerifyCode() throws ServerException {
        client.sendVerifyCode("iUuYWkVVSfaSbY3nKg1dL4", "15822222222");
    }

    @Test
    public void testListSignature() throws ServerException {
        client.listSignature();
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

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contract.pdf");
        ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "contract.pdf");
        Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<String, List<ByteArrayBody>>();
        byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
        String response = client.uploadContract(payload, byteStreamBodyMap);
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

        payload.setEnd_at(new Date());

        payload.setRemark("test");

        payload.setTitle("测试合同");

        payload.setContract_id("iUuYWkVVSfaSbY3nKg1dL4");

        List<String> usePhones = new ArrayList();
        usePhones.add("15822222222");
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
        client.uploadSignature(payload, byteStreamBodyMap);
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
        payload.setSignature_id("e12UdCis3omkaBNxaAS991");
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
    public void signatureList() throws ServerException, IOException {
        SignaturePayload payload = new SignaturePayload();
        client.signatureList(payload);
        //  Assert.assertNotNull(response);
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
