package com.campfire.smeal.service;

import com.campfire.smeal.dto.ReplySaveRequestDto;
//import com.campfire.smeal.dto.utils.Criteria;
//import com.campfire.smeal.dto.utils.Paging;
import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.model.Board;
import com.campfire.smeal.model.Reply;
import com.campfire.smeal.model.User;
import com.campfire.smeal.repository.BoardRepository;
import com.campfire.smeal.repository.ReplyRepository;
import com.campfire.smeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.campfire.smeal.handler.exception.SmErrorCode.INVALID_REQUEST;
import static com.campfire.smeal.handler.exception.SmErrorCode.NO_BOARD;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable, String searchText) {
        //Page<Board> boards = boardRepository.findAll(PageRequest.of(0,20)); // page는 0부터 시작임
        // 전체 데이터 건수 확인 getTotalElemets(), 전체 페이지수: getTotalPages(), 페이지 정보: getPageable()
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(
                searchText, searchText, pageable);

        return boards;
    }

//    @Transactional(readOnly = true)
//    public Map<String, Object> 글목록_테스트(Pageable pageable,
//                                       int cntPerPage
//
//    ) {
//        Map<String, Object> map = new HashMap<>();
//
//        Page<Board> page = boardRepository.findAll(pageable);
//        map.put("boardPage", page);
//
//        //Criteria cri = new Criteria();
//        int size = (int) boardRepository.count();
//        Paging paging = new Paging(page.getPageable().getPageNumber(),
//                cntPerPage,
//                size
//        );
//        map.put("pagingInfo", paging);
//        return map;
//    }

    @Transactional
    public Board 상세보기(int id) {
        Board post = boardRepository.findById(Long.valueOf(id)).orElseThrow(() ->
                new GeneralException(NO_BOARD)
        );
        post.setViews(post.getViews() + 1);
        return post;
    }

    @Transactional
    public void 글쓰기(Board board, User user) {
        board.setViews(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional
    public void 글수정하기(Long id, Board requestBoard) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new GeneralException(NO_BOARD)
        );

        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
    }

    @Transactional
    public void 글삭제하기(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
        int result = replyRepository.mSave(
                replySaveRequestDto.getUserId(),
                replySaveRequestDto.getBoardId(),
                replySaveRequestDto.getContent()
        );
    }

    @Transactional
    public void 댓글수정(int replyId,
                     ReplySaveRequestDto replySaveRequestDto
    ) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() ->
                new GeneralException(INVALID_REQUEST)
        );
        reply.setContent(replySaveRequestDto.getContent());

    }

    @Transactional
    public void 댓글삭제(int replyId) {
        replyRepository.deleteById(replyId);
    }

    @Transactional
    public Long TotalCount() {
        return boardRepository.count();
    }

    @Transactional
    public void myBoard(Long id) {
        System.out.println("myBoard의 id: "+id);
        Collection<Board> boards = boardRepository.findByUserId(id);
        boards.forEach(board-> System.out.println(boards));
    }







}
