package com.study.boot.react.service;

import com.study.boot.react.dto.MemberDto;
import com.study.boot.react.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Value("${file.upload.dir}")
    private String UPLOAD_DIR;

    public void insertMember(MemberDto memberDto) {
        if (memberDto.getProfileFile() != null) {
            MultipartFile multipartFile = memberDto.getProfileFile();
            String path = UPLOAD_DIR + "img/profile/";
            String originalFilename = multipartFile.getOriginalFilename(); // 파일 원래 이름
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 확장자 추출
            String savedFilename = UUID.randomUUID() + extension; // UUID + 확장자

            try {
                File dir = new File(path);
                if (!dir.exists()) dir.mkdirs();
                File dest = new File(path + savedFilename);
                multipartFile.transferTo(dest); // 지정된 경로에 지정한 파일 이름으로 파일 저장
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 중 서버에 오류 발생");
            }

            memberDto.setPicture(savedFilename);
        }

        memberRepository.save(memberDto.toEntity(memberDto));
    }

    public Boolean idCheck(String id) {
        return memberRepository.existsById(id);
    }
}
