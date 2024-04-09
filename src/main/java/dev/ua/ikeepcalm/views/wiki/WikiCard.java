package dev.ua.ikeepcalm.views.wiki;

public class WikiCard {

    private final String question;
    private final String answer;

    public WikiCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}