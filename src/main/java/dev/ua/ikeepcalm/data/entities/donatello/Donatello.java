package dev.ua.ikeepcalm.data.entities.donatello;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Donatello {

    private int page;
    private int size;
    private List<Donation> content;
    private int pages;
    private boolean first;
    private boolean last;
    private int total;

    @Override
    public String toString() {
        return "DonatelloResponse{" +
                "page=" + page +
                ", size=" + size +
                ", content=" + content +
                ", pages=" + pages +
                ", first=" + first +
                ", last=" + last +
                ", total=" + total +
                '}';
    }

}