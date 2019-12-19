package com.baoquan.jsdk.comm;


import com.baoquan.jsdk.Enum.IdentityTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseAttestationPayloadParam implements Serializable {
    @NotNull(message = "factoids不能为空")
    @Size(min = 1)
    private List<PayloadFactoidParam> factoids;

    @NotBlank(message = "unique_id不能为空")
    private String unique_id;

    @NotNull(message = "template_id不能为空")
    private String template_id;

    @NotNull(message = "identities不能为空")
    @Size(min = 1)
    private Map<IdentityTypeEnum, String> identities;
}
