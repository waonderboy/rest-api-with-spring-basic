package com.example.restfulwebservice.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
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
@JsonFilter("UserInfo")
public class User {
    private Integer id;
    @Size(min = 2, message = "두 글자 이상 입력해 주세요")
    private String name;
    private LocalDateTime createdAt;
    private String password;
    private String ssn;

    public User(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

}
