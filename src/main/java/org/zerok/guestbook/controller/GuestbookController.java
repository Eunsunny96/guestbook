package org.zerok.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerok.guestbook.dto.GuestbookDTO;
import org.zerok.guestbook.dto.PageRequestDTO;
import org.zerok.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor //자동주입
public class GuestbookController {

    private final GuestbookService service; //final로 선언

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbbok/list";

    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list............" +pageRequestDTO);

        model.addAttribute("result",service.getList(pageRequestDTO)); //result라는 이름으로 전당
    }
    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }

    @PostMapping("register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){ //RedirectAttributes 한번만 화면에서 msg변수를 사용할수 있도록 처리
        log.info("dto......" +dto);
        Long gno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("gno: " +gno);
        GuestbookDTO dto = service.read(gno);
        model.addAttribute("dto",dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){

        log.info("gno : "+ gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }
}
