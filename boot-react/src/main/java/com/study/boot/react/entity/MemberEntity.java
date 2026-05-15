package com.study.boot.react.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
    @Id
    @Column(length = 20)
    private String id;
    private String pass;
    private String name;
    private int gender;
    private String tel;
    private String email;
    private String profileFileName;
}
