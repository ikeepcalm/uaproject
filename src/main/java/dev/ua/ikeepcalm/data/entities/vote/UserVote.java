package dev.ua.ikeepcalm.data.entities.vote;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserVote {
    @Id
    private String voterId;
    private String voteeId;
}