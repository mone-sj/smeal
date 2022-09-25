package com.campfire.smeal.repository;

import com.campfire.smeal.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    //Page<Board> findByUserId(Long userId, Pageable pageable);

    Collection<Board> findByUserId(Long userId);


}
