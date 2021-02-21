package org.gorany.community.repository;

import org.gorany.community.entity.Board;
import org.gorany.community.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query("DELETE from Reply R WHERE R.board.bno = :bno")
    void deleteByBno(int bno);

    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
