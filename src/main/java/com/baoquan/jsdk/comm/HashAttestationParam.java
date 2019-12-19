package com.baoquan.jsdk.comm;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashAttestationParam extends BaseAttestationPayloadParam {
    @NotBlank(message = "sha256不能为空")
    private String sha256;
}
