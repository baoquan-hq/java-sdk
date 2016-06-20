package com.baoquan.sdk;

import com.baoquan.sdk.exceptions.ServerException;
import com.baoquan.sdk.pojos.payload.*;
import com.baoquan.sdk.pojos.response.AddFactoidsResponse;
import com.baoquan.sdk.pojos.response.ApplyCaResponse;
import com.baoquan.sdk.pojos.response.CreateAttestationResponse;
import com.sun.tools.javac.util.Assert;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    client.setPemPath(getClass().getClassLoader().getResource("private_key.pem").getPath());
  }

  /**
   * payload can not be null
   * @throws ServerException
   */
  @Test
  public void testCreateAttestation0() throws ServerException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("payload can not be null");
    client.createAttestation(null);
  }

  /**
   * payload.templateId can not be empty
   * @throws ServerException
   */
  @Test
  public void testCreateAttestation1() throws ServerException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("payload.templateId can not be empty");
    client.createAttestation(new CreateAttestationPayload());
  }

  /**
   * payload.identities can not be empty
   * @throws ServerException
   */
  @Test
  public void testCreateAttestation2() throws ServerException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("payload.identities can not be empty");
    CreateAttestationPayload payload = new CreateAttestationPayload();
    payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
    client.createAttestation(payload);
  }

  /**
   * payload.identities can not be empty
   * @throws ServerException
   */
  @Test
  public void testCreateAttestation3() throws ServerException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("payload.identities can not be empty");
    CreateAttestationPayload payload = new CreateAttestationPayload();
    payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
    payload.setIdentities(new HashMap<>());
    client.createAttestation(payload);
  }

  /**
   * payload.factoids can not be empty
   * @throws ServerException
   */
  @Test
  public void testCreateAttestation4() throws ServerException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("payload.factoids can not be empty");
    CreateAttestationPayload payload = new CreateAttestationPayload();
    payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
    Map<IdentityType, String> identities = new HashMap<>();
    identities.put(IdentityType.ID, "42012319800127691X");
    identities.put(IdentityType.MO, "15857112383");
    payload.setIdentities(identities);
    client.createAttestation(payload);
  }

  /**
   * payload.factoids can not be empty
   * @throws ServerException
   */
  @Test
  public void testCreateAttestation5() throws ServerException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("payload.factoids can not be empty");
    CreateAttestationPayload payload = new CreateAttestationPayload();
    payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
    Map<IdentityType, String> identities = new HashMap<>();
    identities.put(IdentityType.ID, "42012319800127691X");
    identities.put(IdentityType.MO, "15857112383");
    payload.setIdentities(identities);
    payload.setFactoids(new ArrayList<>());
    client.createAttestation(payload);
  }

  /**
   * template should be exist
   * @throws ServerException
   */
  @Test
  public void testCreateAttestation6() throws ServerException {
    expectedException.expect(ServerException.class);
    expectedException.expectMessage("模板不存在");
    CreateAttestationPayload payload = new CreateAttestationPayload();
    payload.setTemplateId("2hSWTZ4oqVEJ");
    Map<IdentityType, String> identities = new HashMap<>();
    identities.put(IdentityType.ID, "42012319800127691X");
    identities.put(IdentityType.MO, "15857112383");
    payload.setIdentities(identities);
    List<Factoid> factoids = new ArrayList<>();
    Factoid factoid = new Factoid();
    User user = new User();
    user.setName("张三");
    user.setPhone_number("13234568732");
    user.setRegistered_at("2015.06.23");
    user.setUsername("tom");
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
   * @throws ServerException
   */
  @Test
  public void testCreateAttestation7() throws ServerException {
    expectedException.expect(ServerException.class);
    expectedException.expectMessage("invalid data : user.phone_number required");
    CreateAttestationPayload payload = new CreateAttestationPayload();
    payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
    Map<IdentityType, String> identities = new HashMap<>();
    identities.put(IdentityType.ID, "42012319800127691X");
    identities.put(IdentityType.MO, "15857112383");
    payload.setIdentities(identities);
    List<Factoid> factoids = new ArrayList<>();
    Factoid factoid = new Factoid();
    User user = new User();
    user.setName("张三");
    user.setRegistered_at("2015.06.23");
    user.setUsername("tom");
    factoid.setType("user");
    factoid.setData(user);
    factoids.add(factoid);
    payload.setFactoids(factoids);
    client.createAttestation(payload);
  }

  /**
   * factoid data type should be in template schemas
   * @throws ServerException
   */
  @Test
  public void testCreateAttestation8() throws ServerException {
    expectedException.expect(ServerException.class);
    expectedException.expectMessage("invalid factoid type: product corresponding schema not exist");
    CreateAttestationPayload payload = new CreateAttestationPayload();
    payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
    Map<IdentityType, String> identities = new HashMap<>();
    identities.put(IdentityType.ID, "42012319800127691X");
    identities.put(IdentityType.MO, "15857112383");
    payload.setIdentities(identities);
    List<Factoid> factoids = new ArrayList<>();
    Factoid factoid = new Factoid();
    Product product = new Product();
    product.setName("浙金网");
    product.setDescription("p2g理财平台");
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
   * @throws ServerException
   */
  @Test
  public void testCreateAttestation9() throws ServerException {
    expectedException.expect(ServerException.class);
    expectedException.expectMessage("invalid data : user.phone_number required");
    CreateAttestationPayload payload = new CreateAttestationPayload();
    payload.setTemplateId("5Yhus2mVSMnQRXobRJCYgt");
    Map<IdentityType, String> identities = new HashMap<>();
    identities.put(IdentityType.ID, "42012319800127691X");
    identities.put(IdentityType.MO, "15857112383");
    payload.setIdentities(identities);
    List<Factoid> factoids = new ArrayList<>();
    Factoid factoid = new Factoid();
    Product product = new Product();
    product.setName("浙金网");
    product.setDescription("p2g理财平台");
    factoid.setType("product");
    factoid.setData(product);
    factoids.add(factoid);
    payload.setFactoids(factoids);
    client.createAttestation(payload);
  }

  /**
   * payload can not be null
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
   * @throws ServerException
   */
  @Test
  public void testAddFactoids3() throws ServerException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("payload.factoids can not be empty");
    AddFactoidsPayload payload = new AddFactoidsPayload();
    payload.setAno("D58FFFD28A8949969611883B6EABA148");
    payload.setFactoids(new ArrayList<>());
    client.addFactoids(payload);
  }

  /**
   * attestation must be exist
   * @throws ServerException
   */
  @Test
  public void testAddFactoids4() throws ServerException {
    expectedException.expect(ServerException.class);
    expectedException.expectMessage("保全不存在");
    AddFactoidsPayload payload = new AddFactoidsPayload();
    payload.setAno("D58FFFD28A8949");
    List<Factoid> factoids = new ArrayList<>();
    Factoid factoid = new Factoid();
    Product product = new Product();
    product.setName("浙金网");
    product.setDescription("p2g理财平台");
    factoid.setType("product");
    factoid.setData(product);
    factoids.add(factoid);
    payload.setFactoids(factoids);
    client.addFactoids(payload);
  }

  /**
   * attestation completed and can not add factoids
   * @throws ServerException
   */
  @Test
  public void testAddFactoids5() throws ServerException {
    expectedException.expect(ServerException.class);
    expectedException.expectMessage("保全已完成,不能继续追加陈述");
    AddFactoidsPayload payload = new AddFactoidsPayload();
    payload.setAno("4E6457A5A9B94FBFB64E0D08BDFA2BD4");
    List<Factoid> factoids = new ArrayList<>();
    Factoid factoid = new Factoid();
    Product product = new Product();
    product.setName("浙金网");
    product.setDescription("p2g理财平台");
    factoid.setType("product");
    factoid.setData(product);
    factoids.add(factoid);
    payload.setFactoids(factoids);
    client.addFactoids(payload);
  }

  /**
   * when complete attestation, factoids should meet with schemas
   * @throws ServerException
   */
  @Test
  public void testAddFactoids6() throws ServerException {
    CreateAttestationPayload createAttestationPayload = new CreateAttestationPayload();
    createAttestationPayload.setTemplateId("5Yhus2mVSMnQRXobRJCYgt");
    createAttestationPayload.setCompleted(false);
    Map<IdentityType, String> identities = new HashMap<>();
    identities.put(IdentityType.ID, "42012319800127691X");
    identities.put(IdentityType.MO, "15857112383");
    createAttestationPayload.setIdentities(identities);
    List<Factoid> factoids = new ArrayList<>();
    Factoid factoid = new Factoid();
    Product product = new Product();
    product.setName("浙金网");
    product.setDescription("p2g理财平台");
    factoid.setType("product");
    factoid.setData(product);
    factoids.add(factoid);
    createAttestationPayload.setFactoids(factoids);
    CreateAttestationResponse response = client.createAttestation(createAttestationPayload);
    Assert.checkNonNull(response.getRequest_id());
    Assert.checkNonNull(response.getData());
    Assert.checkNonNull(response.getData().getNo());

    String ano = response.getData().getNo();
    AddFactoidsPayload addFactoidsPayload = new AddFactoidsPayload();
    addFactoidsPayload.setAno(ano);
    factoids = new ArrayList<>();
    factoid = new Factoid();
    product = new Product();
    product.setName("浙金网");
    product.setDescription("p2g理财平台");
    factoid.setType("product");
    factoid.setData(product);
    factoids.add(factoid);
    addFactoidsPayload.setFactoids(factoids);
    expectedException.expect(ServerException.class);
    expectedException.expectMessage("invalid data : user.phone_number required");
    client.addFactoids(addFactoidsPayload);
  }

  /**
   * create attestation and then add factoid
   * @throws ServerException
   */
  @Test
  public void testAddFactoids7() throws ServerException {
    CreateAttestationPayload createAttestationPayload = new CreateAttestationPayload();
    createAttestationPayload.setTemplateId("5Yhus2mVSMnQRXobRJCYgt");
    createAttestationPayload.setCompleted(false);
    Map<IdentityType, String> identities = new HashMap<>();
    identities.put(IdentityType.ID, "42012319800127691X");
    identities.put(IdentityType.MO, "15857112383");
    createAttestationPayload.setIdentities(identities);
    List<Factoid> factoids = new ArrayList<>();
    Factoid factoid = new Factoid();
    Product product = new Product();
    product.setName("浙金网");
    product.setDescription("p2g理财平台");
    factoid.setType("product");
    factoid.setData(product);
    factoids.add(factoid);
    createAttestationPayload.setFactoids(factoids);
    CreateAttestationResponse createAttestationResponse = client.createAttestation(createAttestationPayload);
    Assert.checkNonNull(createAttestationResponse.getRequest_id());
    Assert.checkNonNull(createAttestationResponse.getData());
    Assert.checkNonNull(createAttestationResponse.getData().getNo());

    String ano = createAttestationResponse.getData().getNo();
    AddFactoidsPayload addFactoidsPayload = new AddFactoidsPayload();
    addFactoidsPayload.setAno(ano);
    factoids = new ArrayList<>();
    factoid = new Factoid();
    User user = new User();
    user.setName("张三");
    user.setRegistered_at("2015.06.23");
    user.setUsername("tom");
    user.setPhone_number("13452345987");
    factoid.setType("user");
    factoid.setData(user);
    factoids.add(factoid);
    addFactoidsPayload.setFactoids(factoids);
    AddFactoidsResponse addFactoidsResponse = client.addFactoids(addFactoidsPayload);
    Assert.checkNonNull(addFactoidsResponse.getRequest_id());
    Assert.checkNonNull(addFactoidsResponse.getData());
    Assert.check(addFactoidsResponse.getData().isSuccess());
  }

  /**
   * payload can not be null
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
   * seal can not be null when ca type is enterprise
   * @throws ServerException
   */
  @Test
  public void testApplyCa4() throws ServerException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("seal can not be null when ca type is enterprise");
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
    client.applyCa(payload, null);
  }

  /**
   * apply personal ca
   * @throws ServerException
   */
  @Test
  public void testApplyCa5() throws ServerException {
    ApplyCaPayload payload = new ApplyCaPayload();
    payload.setType(CaType.PERSONAL);
    payload.setLinkName("张三");
    payload.setLinkIdCard(randomIDCard());
    payload.setLinkPhone(randomPhone());
    payload.setLinkEmail(randomPhone() + "@qq.com");
    ApplyCaResponse response = client.applyCa(payload, null);
    Assert.checkNonNull(response.getRequest_id());
    Assert.checkNonNull(response.getData());
    Assert.checkNonNull(response.getData().getNo());
  }

  /**
   * apply enterprise ca
   * @throws ServerException
   */
  @Test
  public void testApplyCa6() throws ServerException, IOException {
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
    Assert.checkNonNull(response.getRequest_id());
    Assert.checkNonNull(response.getData());
    Assert.checkNonNull(response.getData().getNo());
  }

  @Test
  public void testSign0() throws ServerException, IOException {
    CreateAttestationPayload payload = new CreateAttestationPayload();
    payload.setTemplateId("2hSWTZ4oqVEJKAmK2RiyT4");
    Map<IdentityType, String> identities = new HashMap<>();
    identities.put(IdentityType.ID, "42012319800127691X");
    identities.put(IdentityType.MO, "15857112383");
    payload.setIdentities(identities);
    List<Factoid> factoids = new ArrayList<>();
    Factoid factoid = new Factoid();
    User user = new User();
    user.setName("张三");
    user.setRegistered_at("2015.06.23");
    user.setUsername("tom");
    user.setPhone_number("13452345987");
    factoid.setType("user");
    factoid.setData(user);
    factoids.add(factoid);
    payload.setFactoids(factoids);
    Map<String, Map<String, Map<String, List<String>>>> iMap = new HashMap<>();
    Map<String, Map<String, List<String>>> jMap = new HashMap<>();
    Map<String, List<String>> signs = new HashMap<>();
    signs.put("F98F99A554E944B6996882E8A68C60B2", Collections.singletonList("甲方（签章）"));
    signs.put("0A68783469E04CAC95ADEAE995A92E65", Collections.singletonList("乙方（签章）"));
    jMap.put("0", signs);
    iMap.put("0", jMap);
    payload.setAttachments(iMap);
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contract.pdf");
    ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "contract.pdf");
    Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<>();
    byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
    CreateAttestationResponse response = client.createAttestation(payload, byteStreamBodyMap);
    Assert.checkNonNull(response.getRequest_id());
    Assert.checkNonNull(response.getData());
    Assert.checkNonNull(response.getData().getNo());
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
    }
    else if (b.length() == 2) {
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
    return (int)((double)(max - min + 1) * Math.random() + (double)min);
  }
}
