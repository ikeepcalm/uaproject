package dev.ua.ikeepcalm.bot.listeners;

import dev.ua.ikeepcalm.bot.EventDispatcher;
import io.graversen.minecraft.rcon.MinecraftRcon;
import io.graversen.minecraft.rcon.service.ConnectOptions;
import io.graversen.minecraft.rcon.service.MinecraftRconService;
import io.graversen.minecraft.rcon.service.RconDetails;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.Duration;

@Component
public class EmporiumListener extends ListenerAdapter implements EventDispatcher {

    @Value("${minecraft.rcon}")
    private String rconUrl;

    @Value("${minecraft.port}")
    private Integer rconPort;

    @Value("${minecraft.password}")
    private String rconPassword;

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String componentId = event.getComponentId();
        if (componentId.split("-")[0].equals("emporium")) {
            if (componentId.split("-")[1].equals("yes")) {
                EmbedBuilder embedBuilder = new EmbedBuilder(event.getMessage().getEmbeds().getFirst());
                embedBuilder.setColor(Color.GREEN);
                embedBuilder.setFooter("Підтвердив: " + event.getUser().getName(), event.getUser().getAvatarUrl());
                event.getChannel().editMessageEmbedsById(event.getMessageId(), embedBuilder.build()).setComponents().queue();
                Guild guild = event.getGuild();
                if (guild == null) {
                    System.out.println("Error: Guild not found!");
                    return;
                }

                final MinecraftRconService minecraftRconService = new MinecraftRconService(
                        new RconDetails(rconUrl, rconPort, rconPassword),
                        ConnectOptions.defaults()
                );

                String nickname = componentId.split("-")[2];
                String intValue = componentId.split("-")[3];

                minecraftRconService.connectBlocking(Duration.ofSeconds(5));
                MinecraftRcon minecraftRcon = minecraftRconService.minecraftRcon().orElseThrow(IllegalStateException::new);
                minecraftRcon.sendAsync(() -> "emporium add " + nickname + " " + intValue);
            } else if (componentId.split("-")[0].equals("declined")) {
                EmbedBuilder embedBuilder = new EmbedBuilder(event.getMessage().getEmbeds().getFirst());
                embedBuilder.setColor(Color.RED);
                embedBuilder.setFooter("Відхилив: " + event.getUser().getName(), event.getUser().getAvatarUrl());
                event.getChannel().editMessageEmbedsById(event.getMessageId(),
                        embedBuilder.build()).setComponents().queue();
            }
        }
    }
}
