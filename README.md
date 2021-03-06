# Baoquan.com API SDK

Welcome to use Baoquan.com API SDK.

## Create Baoquan Client

```java
BaoquanClient client = new BaoquanClient();
client.setHost("https://stg.baoquan.com");
client.setAccessKey("fsBswNzfECKZH9aWyh47fc"); // replace it with your access key
client.setPemPath("path/to/rsa_private.pem");
```

## Create attestation

### Create attestation without sign

```java
CreateAttestationPayload payload = new CreateAttestationPayload();
payload.setTemplateId("5Yhus2mVSMnQRXobRJCYgt");
payload.setCompleted(false);
Map<IdentityType, String> identities = new HashMap<>();
identities.put(IdentityType.ID, "42012319800127691X");
identities.put(IdentityType.MO, "15857112383");
payload.setIdentities(identities);
List<Factoid> factoids = new ArrayList<>();
Factoid factoid = new Factoid();
Product product = new Product();
product.setName("xxx科技有限公司");
product.setDescription("p2g理财平台");
factoid.setType("product");
factoid.setData(product);
factoids.add(factoid);
payload.setFactoids(factoids);
try {
  CreateAttestationResponse response = client.createAttestation(payload);
  System.out.println(response.getData().getNo());
} catch (ServerException e) {
  System.out.println(e.getMessage());
}
```

### Create attestation with sign

```java
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
user.setRegistered_at("1466674609");
user.setUsername("tom");
user.setPhone_number("13452345987");
factoid.setType("user");
factoid.setData(user);
factoids.add(factoid);
payload.setFactoids(factoids);
Map<String, Map<String, Map<String, List<String>>>> iMap = new HashMap<>();
Map<String, Map<String, List<String>>> jMap = new HashMap<>();
Map<String, List<String>> signs = new HashMap<>();
// the key of signs is no of response when call apply ca
signs.put("F98F99A554E944B6996882E8A68C60B2", Collections.singletonList("甲方（签章）"));
signs.put("0A68783469E04CAC95ADEAE995A92E65", Collections.singletonList("乙方（签章）"));
jMap.put("0", signs);
iMap.put("0", jMap);
payload.setSigns(iMap);
InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contract.pdf");
ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "contract.pdf");
Map<String, List<ByteArrayBody>> byteStreamBodyMap = new HashMap<>();
byteStreamBodyMap.put("0", Collections.singletonList(byteArrayBody));
try {
  CreateAttestationResponse response = client.createAttestation(payload);
  System.out.println(response.getData().getNo());
} catch (ServerException e) {
  System.out.println(e.getMessage());
}
```

## Add factoid

```java
AddFactoidsPayload addFactoidsPayload = new AddFactoidsPayload();
addFactoidsPayload.setAno("7F189BBB5FA1451EA8601D0693E36FE7");
factoids = new ArrayList<>();
factoid = new Factoid();
User user = new User();
user.setName("张三");
user.setRegistered_at("1466674609");
user.setUsername("tom");
user.setPhone_number("13452345987");
factoid.setType("user");
factoid.setData(user);
factoids.add(factoid);
addFactoidsPayload.setFactoids(factoids);
try {
  AddFactoidsResponse response = client.addFactoids(addFactoidsPayload);
  System.out.println(response.getData().isSuccess());
} catch (ServerException e) {
  System.out.println(e.getMessage());
}
```

## Get attestation data

```java
try {
  GetAttestationResponse response = client.getAttestation("DB0C8DB14E3C44C7B9FBBE30EB179241", Collections.singletonList("factoids"));
  System.out.println(response.getData().getFactoids());
} catch (ServerException e) {
  System.out.println(e.getMessage());
}
```

The second param value of getAttestation can be null, empty array, array of field "identities", "factoids", "attachments"
if null, the response contains all filed value
if empty array, the response contains all filed value except for field "identities", "factoids", "attachments"
if array of one or more value in "identities", "factoids", "attachments", the response contains the respond field value
if you just want to get the block chain hash you should set null of the second param, because server need more time to connect database, decrypt data when you want to get "identities", "factoids", "attachments" 

## Download attestation

```java
try {
  DownloadFile downloadFile = client.downloadAttestation("7FF4E8F6A6764CD0895146581B2B28AA");
  System.out.println(response.getData().getFileName());
  FileOutputStream fileOutputStream = new FileOutputStream(downloadFile.getFileName());
  IOUtils.copy(downloadFile.getFile(), fileOutputStream);
  fileOutputStream.close();
} catch (ServerException e) {
  System.out.println(e.getMessage());
}
```

## Apply ca

### Apply personal ca

```java
ApplyCaPayload payload = new ApplyCaPayload();
payload.setType(CaType.PERSONAL);
payload.setLinkName("张三");
payload.setLinkIdCard("432982198405237845");
payload.setLinkPhone("13578674532");
payload.setLinkEmail("13578674532@qq.com");
try {
  ApplyCaResponse response = client.applyCa(payload, null);
  System.out.println(response.getData().getNo());
} catch (ServerException e) {
  System.out.println(e.getMessage());
}
```

### Apply enterprise ca

```java
ApplyCaPayload payload = new ApplyCaPayload();
payload.setType(CaType.ENTERPRISE);
payload.setName("xxx科技有限公司");
payload.setIcCode("91330105311263043J");
payload.setOrgCode("311263043");
payload.setTaxCode("330105311263043");
payload.setLinkName("张三");
payload.setLinkIdCard("432982198405237845");
payload.setLinkPhone("13578674532");
payload.setLinkEmail("13578674532@qq.com");
// enterprise seal image, must jpg or png
InputStream inputStream = getClass().getClassLoader().getResourceAsStream("seal.png");
ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(inputStream), ContentType.DEFAULT_BINARY, "seal.png");
try {
  ApplyCaResponse response = client.applyCa(payload, byteArrayBody);
  System.out.println(response.getData().getNo());
} catch (ServerException e) {
  System.out.println(e.getMessage());
}
```

You can look at the unit test for more examples.