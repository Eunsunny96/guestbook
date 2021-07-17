package org.zerok.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerok.guestbook.dto.GuestbookDTO;
import org.zerok.guestbook.dto.PageRequestDTO;
import org.zerok.guestbook.dto.PageResultDTO;
import org.zerok.guestbook.entity.Guestbook;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceTest {
    @Autowired
    private GuestbookService service;

    @Test
    void testRegister(){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title")
                .content("Sample Content")
                .writer("user0")
                .build();

        System.out.println(service.register(guestbookDTO));
    }
    @Test
    void testList(){
        PageRequestDTO pageRequestDTO= PageRequestDTO.builder() //1페이지부터 처리 가능
                .page(1)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO =service.getList(pageRequestDTO);

        System.out.println("Prev:" +resultDTO.isPrev());
        System.out.println("next:" +resultDTO.isNext());
        System.out.println("Total:" +resultDTO.getTotalPage());

        System.out.println("---------------------------------");

        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
            }
        System.out.println("==================================");
        resultDTO.getPageList().forEach(i-> System.out.println(i));
    }

    @Test
    public void testSearch(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc") //제목이나 내용
                .keyword("한글")
                .build();

        PageResultDTO<GuestbookDTO ,Guestbook> resultDTO =service.getList(pageRequestDTO);

        System.out.println("PREV : " +resultDTO.isPrev());
        System.out.println("NEXT : " +resultDTO.isNext());
        System.out.println("TOTAL : " +resultDTO.getTotalPage());

        System.out.println("-----------------------------------------");


        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("==================================");
        resultDTO.getPageList().forEach(i-> System.out.println(i));

    }


}