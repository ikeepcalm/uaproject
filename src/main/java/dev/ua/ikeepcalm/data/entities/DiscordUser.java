package dev.ua.ikeepcalm.data.entities;

import dev.ua.ikeepcalm.views.form.source.LauncherType;
import dev.ua.ikeepcalm.views.form.source.PlayerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class DiscordUser extends PersistentUser {

    @Column
    private String username;
    @Column
    private String discordId;
    @Column
    private String nickname;
    @Column
    private LocalDate birthday;
    @Column
    private String advised;
    @Column
    private String hobbies;
    @Column
    private PlayerType typeOfPlayer;
    @Column
    private LauncherType gameLauncher;
    @Column
    private String task;
    @Column
    private String experience;
    @Column
    private String conflict;
    @Column
    private String memory;
    @Column
    private boolean alreadyTried;
    @Column
    private boolean wasApproved;

    public DiscordUser() {

    }

    @Override
    public String toString() {
        return "DiscordUser{" +
                "username='" + username + '\'' +
                ", discordId='" + discordId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday=" + birthday +
                ", advised='" + advised + '\'' +
                ", hobbies='" + hobbies + '\'' +
                ", typeOfPlayer=" + typeOfPlayer +
                ", gameLauncher=" + gameLauncher +
                ", task='" + task + '\'' +
                ", experience='" + experience + '\'' +
                ", conflict='" + conflict + '\'' +
                ", memory='" + memory + '\'' +
                ", alreadyTried=" + alreadyTried +
                '}';
    }
}
