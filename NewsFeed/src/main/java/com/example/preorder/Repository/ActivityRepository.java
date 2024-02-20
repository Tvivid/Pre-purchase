package com.example.preorder.Repository;

import com.example.preorder.Entity.Activity;
import com.example.preorder.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a WHERE a.userName IN :userNames ORDER BY a.createdAt DESC")
    List<Activity> findByUserNames(@Param("userNames") Set<Long> followIds);


}
