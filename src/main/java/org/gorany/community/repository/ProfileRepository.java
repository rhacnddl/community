package org.gorany.community.repository;

import org.gorany.community.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    @Modifying
    @Query("DELETE FROM Profile P WHERE P.member.account = :account")
    void deleteByAccount(String account);
}
