package org.zerok.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerok.guestbook.dto.GuestbookDTO;
import org.zerok.guestbook.dto.PageRequestDTO;
import org.zerok.guestbook.dto.PageResultDTO;
import org.zerok.guestbook.entity.Guestbook;
import org.zerok.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor //의존성 자동주입
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository; //반드시 final로 선언, jpa 처리를 위해서

    @Override
    public Long register(GuestbookDTO dto) {//파라미터로 전달되는 GuestbookDTO를 변환

        log.info("DTO--------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);


        return null;
    }

    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result =repository.findById(gno);

        return result.isPresent()? entityToDTO(result.get()) :null; // 리턴  GuestbookDTO
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        Page<Guestbook> result=repository.findAll(pageable);

        Function<Guestbook,GuestbookDTO> fn =(entity ->
                entityToDTO(entity));
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public void remove(Long gno) { //아이디 찾아서 DELETE
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) { //제목과 네용만 수정해서 UPDATE

        Optional<Guestbook> result= repository.findById(dto.getGno());
        if(result.isPresent()){
            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }

    }
}
