package dev.ua.ikeepcalm.views.form.source;

public enum PlayerType {

    BUILDER("Будівельник"),
    FARMER("Фермер"),
    ADVENTURER("Пригодник"),
    REDSTONE_ENGINEER("Інженер"),
    FIGHTER("Боєць"),
    OTHER("Інше");

    private final String type;

    PlayerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
