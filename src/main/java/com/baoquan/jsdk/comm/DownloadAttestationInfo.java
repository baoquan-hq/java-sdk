package com.baoquan.jsdk.comm;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadAttestationInfo implements Serializable {
    private String no;

    private String fileName;

    private String chainHash;

    private byte[] fileInputStream;
}
