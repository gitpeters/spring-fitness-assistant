package com.peters.myapp.controller;

import com.peters.myapp.dtos.BusinessResponse;
import com.peters.myapp.service.AIDataProvider;
import com.peters.myapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class ChatController {
    private final ChatService chatService;
    private final AIDataProvider dataProvider;

    public ChatController(ChatService chatService, AIDataProvider dataProvider) {
        this.chatService = chatService;
        this.dataProvider = dataProvider;
    }

    @PostMapping("/chat")
    public String chatWithAI(@RequestBody String message) {
        return chatService.chatWithAI(message);
    }

    @GetMapping
    public BusinessResponse getAllBusiness(){
        return dataProvider.getBusinesses();
    }
}
