package org.gorany.community.repository;

import org.gorany.community.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = "roleSet")
    @Query("SELECT M FROM Member M WHERE M.account = :account")
    Optional<Member> findByAccount(String account);

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = "roleSet")
    @Query("SELECT M FROM Member M")
    List<Member> getMemberList();
}
