package org.zerok.guestbook.dto;


import com.google.common.annotations.VisibleForTesting;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {//다양한 곳에서 사용할 수 있도록 제네릭 타입을 이용, DTO와 Entity 타입 ,리턴타입

    private List<DTO> dtoList; //dto 타입으로 객체를들 보관
    //총페이지 번호
    private int totalPage;
    //현재 페이지 번호
    private int page;
    //목록 사이즈
    private int size;
    //시작페이지와 끝번호
    private int start,end;
    //이전, 다음
    private boolean prev, next;
    //페이지 번호 목록
    private List<Integer> pageList;


    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) { //Page<Entity> , Function<EN, DTO>는 엔티티를 dto 로 변환
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage =result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber()+1; //0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        int tempEnd =(int)(Math.ceil(page/10.0))*10;
        start =tempEnd-9;

        prev =start >1;

        end =totalPage >tempEnd ? tempEnd:totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start,end)
                .boxed()
                .collect(Collectors.toList());
    }


}