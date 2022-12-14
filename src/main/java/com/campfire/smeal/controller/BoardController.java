package com.campfire.smeal.controller;

import com.campfire.smeal.model.Board;
import com.campfire.smeal.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

        return "board/board";
    }

    @GetMapping("/auth/board/{boardId}")
    public String boardDetail(@PathVariable int boardId,
                              Model model) {

        Board post = boardService.상세보기(boardId);
        model.addAttribute("board",
                boardService.상세보기(boardId));
        return "board/boardDetail";
    }

    /*게시판 작성 양식페이지로 이동
    매핑 주소 바꾸기. 회원만 작성가능.*/
    @GetMapping("/board/create")
    public String boardCreate() {
        return "board/boardSaveForm";
    }

    /* 게시판 수정 페이지 이동
     매핑주소 바꾸기. 관리자 및 글쓴이만 수정 가능*/
    @GetMapping("/board/updateForm/{id}")
    public String boardUpdate(@PathVariable int id,
                              Model model
    ) {
        model.addAttribute("board",
                boardService.상세보기(id));
        return "board/boardUpdateForm";
    }

}
