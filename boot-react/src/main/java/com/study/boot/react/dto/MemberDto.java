package com.study.boot.react.dto;

import com.study.boot.react.entity.MemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class MemberDto {
    private String id;
    private String pass;
    private String name;
    private int gender;
    private String tel;
    private String email;
    private MultipartFile profileFile;
    private String picture;

    public MemberEntity toEntity(MemberDto memberDto) {
        return MemberEntity.builder()
                .id(id)
                .pass(pass)
                .name(name)
                .gender(gender)
                .tel(tel)
                .email(email)
                .profileFileName(picture)
                .build();
    }
}
