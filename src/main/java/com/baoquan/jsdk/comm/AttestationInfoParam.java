package com.baoquan.jsdk.comm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.validation.constraints.NotBlank;
import java.util.List;


public class AttestationInfoParam extends BaseAttestationPayloadParam{
    @NotBlank(message = "ano不能为空")
    private String ano;

    private List<String> fields;

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
