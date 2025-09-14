package com.longtoast.bilbil_api;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SignalingController {

    // 클라이언트가 상대방에게 연결 정보를 보낼 때 이 메서드를 사용합니다.
    @MessageMapping("/signal")
    @SendTo("/topic/signal")
    public String signal(String message) {
        // 받은 연결 정보를 다른 모든 클라이언트에게 전달합니다.
        // 이 메시지는 WebRTC 연결에 필요한 Offer, Answer, ICE 정보를 담고 있습니다.
        return message;
    }
}