package com.campfire.smeal.repository;

import com.campfire.smeal.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
