package com.campfire.smeal.controller;

import com.campfire.smeal.dto.utils.Paging;
import com.campfire.smeal.model.Board;
import com.campfire.smeal.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    // 게시판 리스트
    @GetMapping("/auth/board")
    public String boardList(Model model,
                            @PageableDefault(size = 10,
                                    sort = "id",
                                    direction = Sort.Direction.DESC)
                            Pageable pageable,
                            @RequestParam(required = false, defaultValue = "") String searchText
    ) {
        Page<Board> board = boardService.글목록(pageable, searchText);

        int nowPage = board.getPageable().getPageNumber() + 1;
        int startPage = Math.max(1, board.getPageable().getPageNumber() - 4);
        int endPage = Math.min(board.getTotalPages(), board.getPageable().getPageNumber() + 4);

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("board", board);

        return "test/board";
    }

    @GetMapping("/auth/board/{boardId}")
    public String boardDetail(@PathVariable int boardId,
                                            Model model) {

        Board post = boardService.상세보기(boardId);
        model.addAttribute("board",
                boardService.상세보기(boardId));
        return "test/boardDetail";
    }

    /*게시판 작성 양식페이지로 이동
    매핑 주소 바꾸기. 회원만 작성가능.*/
    @GetMapping("/auth/board/create")
    public String boardCreate() {
        return "test/boardSaveForm";
    }

    /* 게시판 수정 페이지 이동
     매핑주소 바꾸기. 관리자 및 글쓴이만 수정 가능*/
    @GetMapping("/auth/board/updateForm/{id}")
    public String boardUpdate(@PathVariable int id,
                                            Model model
    ) {
        model.addAttribute("board",
                boardService.상세보기(id));
        return "test/boardUpdateForm";
    }



    // 글목록 테스트, 추후 삭제예정
    @GetMapping("/auth/boardList")
    public String boardListPageTest(Model model,
                                    @PageableDefault(sort = "id",
                                            direction = Sort.Direction.DESC)
                                    Pageable pageable,
                                    @RequestParam(value = "page", required = false) int nowPage,
                                    @RequestParam(value = "size", required = false) Integer cntPerPage
    ) {

        if (cntPerPage == null) {
            cntPerPage = 10;
        }

        Map<String, Object> result = boardService.글목록_테스트(pageable, cntPerPage);
        Page<Board> board = (Page<Board>) result.get("boardPage");
        Paging paging = (Paging) result.get("paging");
        model.addAttribute("board", board);
        model.addAttribute("pageInfo", paging);
        System.out.println(paging);

        return "test/board";
    }

}
