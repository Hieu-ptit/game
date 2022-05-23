package com.mc.model.dto;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@CompiledJson
public class DetailUser {

    @JsonAttribute(name = "user_id")
    private String userId;

    private String name;

    private String email;

}
