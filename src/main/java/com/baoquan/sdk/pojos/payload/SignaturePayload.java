package com.baoquan.sdk.pojos.payload;

import java.util.Date;
import java.util.List;

/**
 * Created by sbwdlihao on 6/17/16.
 * <p>
 * the payload of create attestation request
 */
public class SignaturePayload extends CreateAttestationPayload {

    private String signature_id;

    public String getSignature_id() {
        return signature_id;
    }

    public void setSignature_id(String signature_id) {
        this.signature_id = signature_id;
    }

}
