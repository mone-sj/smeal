package com.campfire.smeal.utils;

import com.campfire.smeal.model.Board;
import com.campfire.smeal.model.RoleType;
import com.campfire.smeal.model.User;
import com.campfire.smeal.service.BoardService;
import com.campfire.smeal.service.RecipeSearchService;
import com.campfire.smeal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.campfire.smeal.model.RoleType.ROLE_ADMIN;
import static com.campfire.smeal.model.RoleType.ROLE_USER;

@RequiredArgsConstructor
@Component
public class StartupApplicationListener {

    private final UserService userService;

    private final BoardService boardService;

    private final RecipeSearchService recipeSearchService;

    @PostConstruct
    public void init() {
        User admin=User.builder()
                .username("admin")
                .userId("admin")
                .password("1234")
                .nickname("admin")
                .email("admin")
                .build();

        User user1=User.builder()
                .username("test2")
                .userId("test2")
                .password("1234")
                .nickname("test2")
                .email("test2")
                .foodMbti("D")
                .build();

        User user2=User.builder()
                .username("user2")
                .userId("user2")
                .password("1234")
                .nickname("user2")
                .email("user2@email.com")
                .build();

        userService.회원가입(admin, "1234");
        userService.회원가입(user1,"1234");
        userService.회원가입(user2,"1234");


        Board board = Board.builder()
                .content("test1")
                .title("test1")
                .build();
        Board board1=Board.builder()
                .content("test2")
                .title("test2")
                .build();

        boardService.글쓰기(board, user1);
        boardService.글쓰기(board1, user2);
    }

}
