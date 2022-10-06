package com.campfire.smeal.repository;

import com.campfire.smeal.dto.memberStatusDto;
import com.campfire.smeal.dto.searchFood.FoodRankTypeDto;
import com.campfire.smeal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUserId(String UserId);

}
