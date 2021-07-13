package org.zerok.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO { //jpa쪽에서 사용하는 Pageable타입의 객체 생성  ,파라미터

    private int page;
    private int size;

    public PageRequestDTO(){
        this.page=1;
        this.size=10;

    }

    public Pageable getPageable(Sort sort){//1페이지의 경우 0이 될수 있도록 page-1
        return PageRequest.of(page -1, size, sort);
    }

}