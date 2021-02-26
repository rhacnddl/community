package org.gorany.community.repository;

import org.gorany.community.entity.Board;
import org.gorany.community.entity.UploadResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UploadResultRepository extends JpaRepository<UploadResult, Integer> {

    @Modifying
    @Query("DELETE FROM UploadResult U WHERE U.board.bno = :bno")
    void deleteByBno(int bno);

    List<UploadResult> getUploadResultByBoard(Board board);
}
