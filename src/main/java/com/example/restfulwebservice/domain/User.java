package com.example.restfulwebservice.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 객체")
public class User {
    private Integer id;
    @Size(min = 2, message = "두 글자 이상 입력해 주세요")
    @ApiModelProperty(notes = "사용자 이름 입력")
    private String name;
    private LocalDateTime createdAt;
    private String password;
    private String ssn;

    public User(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

}
