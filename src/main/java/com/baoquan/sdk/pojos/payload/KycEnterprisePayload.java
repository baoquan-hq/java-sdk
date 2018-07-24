package com.baoquan.sdk.pojos.payload;

public class KycEnterprisePayload {

  private String phone;


  private String name;


  private String orgcode;


  private String accountName;


  private String bank;


  private String bankAccount;

  private String contactName;

  private String contactCode;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOrgcode() {
    return orgcode;
  }

  public void setOrgcode(String orgcode) {
    this.orgcode = orgcode;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getBank() {
    return bank;
  }

  public void setBank(String bank) {
    this.bank = bank;
  }

  public String getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(String bankAccount) {
    this.bankAccount = bankAccount;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactCode() {
    return contactCode;
  }

  public void setContactCode(String contactCode) {
    this.contactCode = contactCode;
  }
}
