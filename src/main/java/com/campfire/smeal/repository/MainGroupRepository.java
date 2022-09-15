package com.campfire.smeal.repository;

import com.campfire.smeal.model.search.MainGroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainGroupRepository extends JpaRepository<MainGroupInfo, Integer> {
}
