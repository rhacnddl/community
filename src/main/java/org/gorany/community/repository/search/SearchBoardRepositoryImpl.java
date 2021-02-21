package org.gorany.community.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.gorany.community.entity.Board;
import org.gorany.community.entity.QBoard;
import org.gorany.community.entity.QMember;
import org.gorany.community.entity.QReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {

       log.info("SearchBoardRepositoryImpl....");

       QBoard board = QBoard.board;
       QReply reply = QReply.reply;
       QMember member = QMember.member;

       JPQLQuery<Board> jpqlQuery = from(board);

       jpqlQuery.leftJoin(member).on(board.writer.eq(member));
       jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

       //jpqlQuery.select(board, member.account, reply.count()).groupBy(board);
       JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.account, reply.count()).groupBy(board);

       log.info("----------------------");
       log.info(tuple);

        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0);

        builder.and(expression);

        if(type != null){
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t : typeArr){
                if(t.equals("t"))
                    conditionBuilder.or(board.title.contains(keyword));
                else if(t.equals("c"))
                    conditionBuilder.or(board.content.contains(keyword));
                else if(t.equals("w")){
                    conditionBuilder.or(member.account.contains(keyword)).and(board.anonymous.eq(false));
                }
            }
            builder.and(conditionBuilder);
        }

        tuple.where(builder);
        tuple.groupBy(board);

        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC:Order.DESC;
            String prop = order.getProperty();

            log.info("Property : " + prop);

            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        long count = tuple.fetchCount();
        log.info(result + " " + count);

        return new PageImpl<Object[]>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }
}
