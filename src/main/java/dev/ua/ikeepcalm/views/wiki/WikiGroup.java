package dev.ua.ikeepcalm.views.wiki;

import java.util.ArrayList;
import java.util.List;

public class WikiGroup {

    private String header;
    private List<WikiCard> questions;


    public WikiGroup(String header) {
        this.header = header;
        this.questions = new ArrayList<>();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void addQuestion(WikiCard question) {
        questions.add(question);
    }

    public List<WikiCard> getQuestions() {
        return questions;
    }

    public void setQuestions(List<WikiCard> questions) {
        this.questions = questions;
    }
}
