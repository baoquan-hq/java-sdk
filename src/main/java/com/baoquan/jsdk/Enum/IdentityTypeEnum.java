package com.baoquan.jsdk.Enum;


public enum IdentityTypeEnum {

    ID("ID", "身份证号码"),
    USCID("USCID", "统一社会信用代码"),

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
