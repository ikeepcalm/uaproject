package dev.ua.ikeepcalm.views.wiki;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class WikiGroup {

    private String header;
    private List<WikiCard> questions;


    public WikiGroup(String header) {
        this.header = header;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(WikiCard question) {
        questions.add(question);
    }

}
