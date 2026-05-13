package com.study.boot.react.controller;

import com.study.boot.react.dto.BoardDto;
import com.study.boot.react.entity.BoardEntity;
import com.study.boot.react.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/*
 * @CrossOrigin: CORS(Cross-Origin Resource Sharing) 설정
 *  -Spring Boot 환경에서 다른 도메인과 자원 공유 허용
 * origins = "http://localhost:5173": 주소의 요청만 허용
 *                                    origins="*": 모든 도메인과 자원 공유
 * allowCredentials="true": 민감 정보 허용 (인증정보 등)
 *                          origins="*"인 경우 true 사용불가
 */
@Slf4j
@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Value("${board.upload.dir}")
    private String UPLOAD_PATH;

    @GetMapping("/boardList")
    public Map<String, Object> boardList(@RequestParam Map<String, String> param) {
        Integer pageInt = null;
        for (String key : param.keySet()) {
            if (param.get(key) == null || param.get(key).trim().isEmpty()) {
                param.put(key, null);
            }
        }
        if (param.get("page") != null) {
            pageInt = Integer.parseInt(param.get("page"));
        } else {
            pageInt = 1;
        }
        String boardId = param.get("boardId");
        if (boardId == null) boardId = "1";
        String boardName = null;
        switch (boardId) {
            case "1" -> boardName = "공지사항";
            case "2" -> boardName = "자유게시판";
            case "3" -> boardName = "QNA";
        }
        int limit = 10;
        int listCount = boardService.boardCount(boardId);
        List<BoardEntity> bList = boardService.boardList(pageInt, limit, boardId).getContent();

        int bottomLine = 10; // 한화면에 보여질 페이짓 갯수
        int start = (pageInt - 1) / bottomLine * bottomLine + 1; // 시작페이지 번호
        int end = start + bottomLine - 1; // 끝 페이지 번호
        int maxPage = (listCount / limit) + (listCount % limit == 0 ? 0 : 1); // 최대 페이지 번호
        if (end > maxPage) end = maxPage;
        /*
         * 1. Map map = new HashMap<>();
         *    map.put("boardId", boardId);
         *    ...
         * 2. Map.of(k1, v1, k2, v2 ... )
         *  Java9에 추가됨
         *  변경불가 객체
         *  최대 10개만 지원
         */

        return Map.of(
                "boardId", boardId,
                "boardName", boardName,
                "pageInt", pageInt,
                "maxPage", maxPage,
                "start", start,
                "end", end,
                "listCount", listCount,
                "bList", bList,
                "bottomLine", bottomLine
        );
    }

    @PostMapping("/boardPro")
    public BoardEntity boardPro(@RequestParam(value = "file2", required = false) MultipartFile multipartFile, BoardDto boardDto) {
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
        }
        boardDto.setFile1(fileName); // 첨부파일 이름
        return boardService.insertBoard(new BoardEntity(boardDto));
    }

    @GetMapping("/boardInfo")
    public Map<String, Object> boardInfo(@RequestParam int num) {
        BoardEntity board = boardService.getBoard(num);
        boardService.addReadCount(num);
        String boardName = null;
        switch (Objects.requireNonNullElse(board.getBoardid(), "1")) {
            case "1" -> boardName = "공지사항";
            case "2" -> boardName = "자유게시판";
            case "3" -> boardName = "QNA";
        }
        ;
        return Map.of("board", board, "boardName", boardName);
    }

    @PostMapping("/boardUpdatePro")
    public ResponseEntity<BoardEntity> boardUpdatePro(@RequestParam(value = "file2", required = false) MultipartFile multipartFile, BoardDto boardDto) {
        try {
            BoardEntity boardEntity = boardService.updateBoard(multipartFile, boardDto);
            return ResponseEntity.status(HttpStatus.OK).body(boardEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
