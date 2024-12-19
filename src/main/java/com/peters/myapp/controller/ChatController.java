package com.peters.myapp.controller;

import com.peters.myapp.dtos.BusinessResponse;
import com.peters.myapp.service.AIDataProvider;
import com.peters.myapp.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public String chatWithAI(@RequestBody String message) {
        return chatService.chatWithAI(message);
    }
}
