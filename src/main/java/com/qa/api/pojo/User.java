package com.qa.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL) //Include only non null parameters as id will be null while request
public class User {

    private Integer id;
    private String name;
    private String email;
    private String gender;
    private String status;

}
