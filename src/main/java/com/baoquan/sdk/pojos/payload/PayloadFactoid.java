package com.baoquan.sdk.pojos.payload;

import java.util.LinkedHashMap;

public class PayloadFactoid {
    private String unique_id;

    private String type;

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
