package com.example.preorder.Repository;

import com.example.preorder.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<Member, Long> {
}
