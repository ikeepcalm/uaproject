package dev.ua.ikeepcalm.views.wiki;

public record WikiCard(String question, String answer, String filePath) {

    public WikiCard(String question, String answer, String filePath) {
        this.answer = answer;
        this.question = question;
        this.filePath = filePath;
    }

    public WikiCard(String question, String answer) {
        this(question, answer, null);
    }

}