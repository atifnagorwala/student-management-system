package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.AIRequest;
import com.example.studentmanagement.dto.AIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Service
public class AIService {

    @Value("${ai.api.key}")
    private String apiKey;

    private final RestClient restClient;

    public AIService() {
        this.restClient = RestClient.create();
    }

    public AIResponse askAI(AIRequest request) {

        try {

            // Create request data
            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put("model", "gpt-5.6-luna");
            requestBody.put("input", request.getQuestion());

            // Send request
            String response = restClient.post()
                    .uri("https://api.openai.com/v1/responses")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            System.out.println("========== AI API RESPONSE ==========");
            System.out.println(response);
            System.out.println("=====================================");

            String answer = extractAnswer(response);

            return new AIResponse(answer);

        } catch (RestClientResponseException e) {

            System.out.println("========== AI API ERROR ==========");
            System.out.println("Status Code: " + e.getStatusCode());
            System.out.println("Response Body: " + e.getResponseBodyAsString());
            System.out.println("==================================");

            return new AIResponse(
                    "AI API Error: " + e.getStatusCode()
            );

        } catch (Exception e) {

            System.out.println("========== JAVA ERROR ==========");
            e.printStackTrace();
            System.out.println("================================");

            return new AIResponse(
                    "Backend Error: " + e.getMessage()
            );
        }
    }

    private String extractAnswer(String response) {

        String marker = "\"text\": \"";

        int textIndex = response.indexOf(marker);

        if (textIndex == -1) {
            return "Could not find AI answer in the response.";
        }

        int start = textIndex + marker.length();

        StringBuilder answer = new StringBuilder();

        boolean escaped = false;

        for (int i = start; i < response.length(); i++) {

            char c = response.charAt(i);

            if (escaped) {

                if (c == 'n') {
                    answer.append('\n');
                } else if (c == 't') {
                    answer.append('\t');
                } else if (c == 'r') {
                    answer.append('\r');
                } else if (c == '"') {
                    answer.append('"');
                } else if (c == '\\') {
                    answer.append('\\');
                } else {
                    answer.append(c);
                }

                escaped = false;

            } else if (c == '\\') {

                escaped = true;

            } else if (c == '"') {

                break;

            } else {

                answer.append(c);
            }
        }

        return answer.toString();
    }
}