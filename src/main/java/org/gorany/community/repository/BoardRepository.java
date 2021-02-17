package org.gorany.community.repository;

import org.gorany.community.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    /*@Query("SELECT B, W FROM Board B " +
            "LEFT JOIN B.writer W")
    List<Object[]> getList();*/

    @Query(value = "SELECT B, W, COUNT(R) FROM Board B " +
            "LEFT JOIN B.writer W " +
            "LEFT JOIN Reply R ON R.board = B " +
            "GROUP BY B",
    countQuery = "SELECT COUNT(B) FROM Board B")
    Page<Object[]> getListWithPaging(Pageable pageable);

    @Query("SELECT B, W, COUNT(R) FROM Board B " +
            "LEFT OUTER JOIN B.writer W " +
            "LEFT OUTER JOIN Reply R ON R.board = B " +
            "WHERE B.bno = :bno")
    Object readBoard(@Param("bno") int bno);
}
