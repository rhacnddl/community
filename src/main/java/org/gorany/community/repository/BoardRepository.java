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

    @Query("SELECT B, W FROM Board B LEFT JOIN B.writer W")
    Page<Object[]> getListWithPaging(Pageable pageable);

    @Query("SELECT B, W FROM Board B LEFT JOIN B.writer W WHERE B.bno = :bno")
    Object readBoard(@Param("bno") int bno);
}
