package com.baoquan.jsdk.comm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashMap;


public class PayloadFactoidParam implements Serializable {
    @NotBlank(message = "unique_id不能为空")
    private String unique_id;

    @NotBlank(message = "type不能为空")
    private String type;

    @NotNull(message = "data不能为空")
    private LinkedHashMap data;

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinkedHashMap getData() {
        return data;
    }

    public void setData(LinkedHashMap data) {
        this.data = data;
    }
}
