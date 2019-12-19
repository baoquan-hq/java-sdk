package com.baoquan.jsdk.comm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttestationInfoParam extends BaseAttestationPayloadParam{
    @NotBlank(message = "ano不能为空")
    private String ano;

    private List<String> fields;
}
