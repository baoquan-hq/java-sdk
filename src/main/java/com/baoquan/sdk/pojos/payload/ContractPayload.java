package com.baoquan.sdk.pojos.payload;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sbwdlihao on 6/17/16.
 * <p>
 * the payload of create attestation request
 */
public class ContractPayload extends CreateAttestationPayload {

    private String title;
    private Date end_at;
    private String remark;
    private List<String> userPhones;
//    private String status;
//    private String name;
    private String contract_id;
    private String group_id;
    private Boolean yourself;

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEnd_at() {
        return end_at;
    }

    public void setEnd_at(Date end_at) {
        this.end_at = end_at;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getUserPhones() {
        return userPhones;
    }

    public void setUserPhones(List<String> userPhones) {
        this.userPhones = userPhones;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public Boolean getYourself() {
        return yourself;
    }

    public void setYourself(Boolean yourself) {
        this.yourself = yourself;
    }

    //    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }


//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

}
