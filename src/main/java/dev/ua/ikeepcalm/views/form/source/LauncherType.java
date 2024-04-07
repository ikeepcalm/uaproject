package dev.ua.ikeepcalm.views.form.source;

public enum LauncherType {
    TLAUNCHER("Tlauncher"),
    TLAUNCHER_LEGACY("Tlauncher Legacy"),
    MINECRAFT_LAUNCHER("Minecraft Launcher"),
    BEDROCK("Bedrock"),
    PRISM_LAUNCHER("Prism Launcher"),
    POLYMC("PolyMC"),
    POLLYMC("PollyMC"),
    MULTI_MC("MultiMC"),
    OTHER("Other");

    private final String launcher;

    LauncherType(String launcher) {
        this.launcher = launcher;
    }

    public String getLauncher() {
        return launcher;
    }
}
