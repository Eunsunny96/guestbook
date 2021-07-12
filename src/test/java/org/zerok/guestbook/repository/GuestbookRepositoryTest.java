package org.zerok.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerok.guestbook.entity.Guestbook;
import org.zerok.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    GuestbookRepository guestbookRepository;

    @Test
    void insertTest(){
        IntStream.rangeClosed(1,300).forEach( i -> {
                    Guestbook guestbook = Guestbook.builder()
                            .title("Title .." +i)
                            .content("Content .." +i)
                            .writer("user" +(i%10))
                            .build();
                    System.out.println(guestbookRepository.save(guestbook));
                }
                );
    }

    @Test
    void updateTest(){
        Optional<Guestbook> result = guestbookRepository.findById(1300L);

        if(result.isPresent()){

            Guestbook guestbook = result.get();

            guestbook.changeTitle("Change Title");
            guestbook.changeContent("Change Content");

            guestbookRepository.save(guestbook);
        }
    }

    @Test
    void testQuery1(){
        Pageable pageable= PageRequest.of(0,10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword="1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expression = qGuestbook.title.contains(keyword);

        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
    @Test
    void testQuery2(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);

        BooleanExpression exContent = qGuestbook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);
        result.stream().forEach(guestbook ->{
                    System.out.println(guestbook);
                }
                );


    }


}