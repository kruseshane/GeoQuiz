package com.example.geoquiz;

public class Question {

    private int question;
    private boolean answer;
    private boolean cheat;

    public Question(int question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public int getQuestion() {
        return question;
    }

    public boolean isAnswer() {
        return answer;
    }
}
