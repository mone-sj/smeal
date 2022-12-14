package com.campfire.smeal.controller.api;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.ReplySaveRequestDto;
import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.model.Board;
import com.campfire.smeal.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/board/save")
    public ResponseDto<Integer> save(@RequestBody Board board,
                                     @AuthenticationPrincipal
                                     PrincipalDetails principal
    ) {
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/board/update/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id,
                                       @RequestBody Board board) {
        boardService.글수정하기(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/board/delete/{id}")
    public ResponseDto<Integer> deleteBoard(
            @PathVariable Long id
    ) {
        boardService.글삭제하기(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


    @PostMapping("/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(
            @RequestBody ReplySaveRequestDto replySaveRequestDto) {
        boardService.댓글쓰기(replySaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyUpdate(
            @PathVariable int replyId,
            @RequestBody ReplySaveRequestDto replySaveRequestDto
    ) {
        boardService.댓글수정(replyId,replySaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
        boardService.댓글삭제(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
