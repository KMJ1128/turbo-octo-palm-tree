package com.longtoast.bilbil_api;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    private final List<String> messages = new ArrayList<>();

    @GetMapping("/")
    public String showGuestbook(Model model) {
        model.addAttribute("messages", messages);
        return "Test";
    }

    @PostMapping("/submit")
    public String receiveMessage(@RequestParam String message) {
        messages.add(message);
        return "redirect:/"; // 메시지 제출 후, 다시 메인 페이지로 리디렉션
    }
}
