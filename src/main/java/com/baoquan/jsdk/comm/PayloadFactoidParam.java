package com.baoquan.jsdk.comm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayloadFactoidParam implements Serializable {
    @NotBlank(message = "unique_id不能为空")
    private String unique_id;

    @NotBlank(message = "type不能为空")
    private String type;

    @NotNull(message = "data不能为空")
    private LinkedHashMap data;
}
