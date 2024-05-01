package dev.ua.ikeepcalm.data.entities.donatello;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Donation {
    private String pubId;
    private String clientName;
    private String message;
    private int pages;
    private boolean first;
    private String amount;
    private boolean last;
    private int total;
    private String currency;
    private boolean isPublished;

    @Override
    public String toString() {
        return "Donation{" +
                "pubId='" + pubId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", message='" + message + '\'' +
                ", pages=" + pages +
                ", first=" + first +
                ", amount='" + amount + '\'' +
                ", last=" + last +
                ", total=" + total +
                ", currency='" + currency + '\'' +
                ", isPublished=" + isPublished +
                '}';
    }
}