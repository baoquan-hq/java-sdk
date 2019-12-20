package com.baoquan.jsdk.comm;


import com.baoquan.jsdk.Enum.IdentityTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class BaseAttestationPayloadParam implements Serializable {
    private List<PayloadFactoidParam> factoids;

    @NotBlank(message = "unique_id不能为空")
    private String unique_id;

    @NotNull(message = "template_id不能为空")
    private String template_id;

    @NotNull(message = "identities不能为空")
    @Size(min = 1)
    private Map<IdentityTypeEnum, String> identities;

    public List<PayloadFactoidParam> getFactoids() {
        return factoids;
    }

    public void setFactoids(List<PayloadFactoidParam> factoids) {
        this.factoids = factoids;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public Map<IdentityTypeEnum, String> getIdentities() {
        return identities;
    }

    public void setIdentities(Map<IdentityTypeEnum, String> identities) {
        this.identities = identities;
    }
}
