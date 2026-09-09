package com.example.studentmanagement.dto;

public class AIResponse {

    private String answer;

    public AIResponse() {
    }

    public AIResponse(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}