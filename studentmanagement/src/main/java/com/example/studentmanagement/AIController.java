package com.example.studentmanagement;

import com.example.studentmanagement.dto.AIRequest;
import com.example.studentmanagement.dto.AIResponse;
import com.example.studentmanagement.service.AIService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@CrossOrigin
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/ask")
    public AIResponse askAI(@RequestBody AIRequest request) {

        return aiService.askAI(request);
    }
}