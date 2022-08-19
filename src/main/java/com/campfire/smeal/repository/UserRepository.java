package com.campfire.smeal.repository;

import com.campfire.smeal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

    public User findByUserId(String userId);
}
