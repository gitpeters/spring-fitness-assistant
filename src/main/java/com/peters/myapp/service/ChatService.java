package com.peters.myapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chatWithAI(String query){
        return
                this.chatClient
                        .prompt()
                        .user(u->{
                            u.text(query);
                        })
                        .call()
                        .content();
    }
}
