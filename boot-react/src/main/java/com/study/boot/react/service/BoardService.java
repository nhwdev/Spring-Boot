package com.study.boot.react.service;

import com.study.boot.react.dto.BoardDto;
import com.study.boot.react.entity.BoardEntity;
import com.study.boot.react.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Value("${file.upload.dir}")
    private String UPLOAD_PATH;

    public int boardCount(String boardId) {
        Specification<BoardEntity> specification = (root, query, cri) -> cri.equal(root.get("boardid"), boardId);
        return (int) boardRepository.count(specification); // long count(): 레코드 건수 조회
    }

    public Page<BoardEntity> boardList(Integer pageInt, int limit, String boardId) {
        Specification<BoardEntity> specification = (root, query, cri) -> cri.equal(root.get("boardid"), boardId);
        // PageRequest.of(페이지 번호(0부터 시작), 페이지 당 보여질 레코드갯수, 정렬방식)
        Pageable pageable = PageRequest.of(pageInt - 1, limit, Sort.by(Sort.Order.desc("num")));
        return boardRepository.findAll(specification, pageable);
    }

    public BoardEntity insertBoard(BoardEntity boardEntity) {
        return boardRepository.save(boardEntity);
    }

    public BoardEntity getBoard(int num) {
        return boardRepository.findById(num).orElse(null);
    }

    public void addReadCount(int num) {
        boardRepository.addReadCount(num);
    }

    public BoardEntity updateBoard(MultipartFile multipartFile, BoardDto boardDto) {

        BoardEntity target = boardRepository.findById(boardDto.getNum()).orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));

        if (!target.getPass().equals(boardDto.getPass())) throw new RuntimeException("비밀번호가 일치하지 않습니다.");

        String path = UPLOAD_PATH + "img/board/";
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();
        String fileName = "";
        if (multipartFile != null && !multipartFile.isEmpty()) {
            File file = new File(path, multipartFile.getOriginalFilename());
            fileName = multipartFile.getOriginalFilename();
            try {
                multipartFile.transferTo(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            boardDto.setFile1(fileName);
        }

        BoardEntity boardEntity = new BoardEntity(boardDto);
        target.patch(boardEntity);
        return boardRepository.save(target);
    }

    public void deleteBoard(Map<String, ?> payload) {
        int num = Integer.parseInt(String.valueOf(payload.get("num")));
        BoardEntity target = boardRepository.findById(num).
                orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        String password = (String) payload.get("pass");
        if (!target.getPass().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        boardRepository.delete(target);
    }

//    public void boardUpdate(BoardEntity boardEntity) {
//        boardRepository.save(boardEntity);
//    }
}
