package com.study.basic.dto;


import com.study.basic.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MemberForm {
    private Long id;
    private String email;
    private String password;

    public Member toEntity() {
        return new Member(id, email, password);
    }
}
