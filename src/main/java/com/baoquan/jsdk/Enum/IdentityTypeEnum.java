package com.baoquan.jsdk.Enum;


public enum IdentityTypeEnum {

    ID("ID", ""),
    USCID("USCID", ""),

    ;

    private String code;

    private String message;

    IdentityTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
