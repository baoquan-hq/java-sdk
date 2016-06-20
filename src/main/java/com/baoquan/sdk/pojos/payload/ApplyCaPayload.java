package com.baoquan.sdk.pojos.payload;

/**
 * Created by sbwdlihao on 6/17/16.
 *
 * the payload of apply ca request
 */
public class ApplyCaPayload {

  /**
   * ca type
   */
  private CaType type;

  /**
   * enterprise name, must not be empty when type is enterprise
   */
  private String name;

  /**
   * license, must not be empty when type is enterprise
   */
  private String icCode;

  /**
   * organization code, must not be empty when type is enterprise
   */
  private String orgCode;

  /**
   * tax code, must not be empty when type is enterprise
   */
  private String taxCode;

  /**
   * linkman name when type is enterprise; personal name when type is personal
   */
  private String linkName;

  /**
   * linkman identity card when type is enterprise; personal identity card when type is personal
   */
  private String linkIdCard;

  /**
   * linkman phone number when type is enterprise; personal phone number when type is personal
   */
  private String linkPhone;

  /**
   * linkman email when type is enterprise; personal email when type is personal
   */
  private String linkEmail;

  public CaType getType() {
    return type;
  }

  public void setType(CaType type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIcCode() {
    return icCode;
  }

  public void setIcCode(String icCode) {
    this.icCode = icCode;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getTaxCode() {
    return taxCode;
  }

  public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }

  public String getLinkName() {
    return linkName;
  }

  public void setLinkName(String linkName) {
    this.linkName = linkName;
  }

  public String getLinkIdCard() {
    return linkIdCard;
  }

  public void setLinkIdCard(String linkIdCard) {
    this.linkIdCard = linkIdCard;
  }

  public String getLinkPhone() {
    return linkPhone;
  }

  public void setLinkPhone(String linkPhone) {
    this.linkPhone = linkPhone;
  }

  public String getLinkEmail() {
    return linkEmail;
  }

  public void setLinkEmail(String linkEmail) {
    this.linkEmail = linkEmail;
  }
}
