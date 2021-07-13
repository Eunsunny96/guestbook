package org.zerok.guestbook.service;

import org.zerok.guestbook.dto.GuestbookDTO;
import org.zerok.guestbook.dto.PageRequestDTO;
import org.zerok.guestbook.dto.PageResultDTO;
import org.zerok.guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);
    GuestbookDTO read(Long gno);

    PageResultDTO<GuestbookDTO,Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestbookDTO entityToDTO(Guestbook entity){
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;

    }
    void remove(Long gno);

    void modify(GuestbookDTO dto);

}
