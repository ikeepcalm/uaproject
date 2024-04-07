package dev.ua.ikeepcalm.data.entities;

import dev.ua.ikeepcalm.views.form.source.LauncherType;
import dev.ua.ikeepcalm.views.form.source.PlayerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.Date;

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

    public DiscordUser(String username, String discordId) {
        this.username = username;
        this.discordId = discordId;
    }

    public DiscordUser() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public boolean isAlreadyTried() {
        return alreadyTried;
    }

    public void setAlreadyTried(boolean alreadyTried) {
        this.alreadyTried = alreadyTried;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAdvised() {
        return advised;
    }

    public void setAdvised(String advised) {
        this.advised = advised;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public PlayerType getTypeOfPlayer() {
        return typeOfPlayer;
    }

    public void setTypeOfPlayer(PlayerType typeOfPlayer) {
        this.typeOfPlayer = typeOfPlayer;
    }

    public LauncherType getGameLauncher() {
        return gameLauncher;
    }

    public void setGameLauncher(LauncherType gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getConflict() {
        return conflict;
    }

    public void setConflict(String conflict) {
        this.conflict = conflict;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
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
