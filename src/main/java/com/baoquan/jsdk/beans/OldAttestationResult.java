package com.baoquan.jsdk.beans;

import com.baoquan.jsdk.comm.OldResultModel;
import lombok.Builder;
import lombok.Data;


@Data
public class OldAttestationResult extends OldResultModel {
    private BaseResultData data;
    private String request_id;
}
