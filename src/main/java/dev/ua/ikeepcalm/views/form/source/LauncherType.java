package dev.ua.ikeepcalm.views.form.source;

public enum LauncherType {
    TLAUNCHER("Tlauncher"),
    TLAUNCHER_LEGACY("Tlauncher Legacy"),
    KLAUNCHER("Klauncher"),
    AT_LAUNCHER("ATLauncher"),
    MINECRAFT_LAUNCHER("Minecraft Launcher"),
    BEDROCK_LAUNCHER("Bedrock Launcher"),
    PRISM_LAUNCHER("Prism Launcher"),
    POLYMC("PolyMC"),
    POLLYMC("PollyMC"),
    MULTI_MC("MultiMC"),
    OTHER("Інший");

    private final String launcher;

    LauncherType(String launcher) {
        this.launcher = launcher;
    }

    public String getLauncher() {
        return launcher;
    }
}
