package dev.ua.ikeepcalm.bot.listeners;

import com.github.t9t.minecraftrconclient.RconClient;
import dev.ua.ikeepcalm.bot.EventDispatcher;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class EmporiumListener extends ListenerAdapter implements EventDispatcher {

    private static final Logger log = LoggerFactory.getLogger(EmporiumListener.class);
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

                String nickname = componentId.split("-")[2];
                String intValue = componentId.split("-")[3];

                try (RconClient client = RconClient.open(rconUrl, rconPort, rconPassword)) {
                    client.sendCommand("emporium add " + nickname + " " + intValue);
                } catch (Exception e) {
                    log.error("Error while sending command to Minecraft server", e);
                }

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
