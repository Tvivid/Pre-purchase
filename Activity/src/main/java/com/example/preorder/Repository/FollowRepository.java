package com.example.preorder.Repository;

import com.example.preorder.Entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("SELECT f.following FROM Follow f WHERE f.follower = :followerId")
    Set<Long> findFollowingIdsByFollowerId(@Param("followerId") Long followerId);
}
