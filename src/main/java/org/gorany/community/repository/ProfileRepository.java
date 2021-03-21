package org.gorany.community.repository;

import org.gorany.community.entity.Member;
import org.gorany.community.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    @Modifying
    @Query("DELETE FROM Profile P WHERE P.member.account = :account")
    void deleteByAccount(String account);

    @Query("SELECT P FROM Profile P WHERE P.member = :member")
    Optional<Profile> findByMember(Member member);

    @Query("SELECT P FROM Profile P WHERE P.member.account = :account")
    Optional<Profile> findByAccount(String account);
}
