package dev.ua.ikeepcalm.bot.listeners;

import com.github.t9t.minecraftrconclient.RconClient;
import dev.ua.ikeepcalm.bot.EventDispatcher;
import dev.ua.ikeepcalm.data.entities.DiscordUser;
import dev.ua.ikeepcalm.data.services.DiscordUserService;
import dev.ua.ikeepcalm.utils.ResponseUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Optional;

@Component
public class FormsListener extends ListenerAdapter implements EventDispatcher {

    private static final Logger log = LoggerFactory.getLogger(FormsListener.class);
    private final DiscordUserService discordUserService;

    @Value("${minecraft.rcon}")
    private String rconUrl;

    @Value("${minecraft.port}")
    private Integer rconPort;

    @Value("${minecraft.password}")
    private String rconPassword;

    @Value("${discord.guest-role-id}")
    private long roleToRemoveId;

    @Value("${discord.player-role-id}")
    private long roleToAddId;

    @Value("${discord.fallback-channel-id}")
    private long fallbackChannelId;

    public FormsListener(DiscordUserService discordUserService) {
        this.discordUserService = discordUserService;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String componentId = event.getComponentId();
        String discordId = componentId.split("-")[1];
        Optional<DiscordUser> user = discordUserService.findByDiscordId(discordId);

        if (user.isEmpty()) {
            return;
        }
        DiscordUser discordUser = user.get();
        if (componentId.split("-")[0].equals("approved")) {
            EmbedBuilder embedBuilder = new EmbedBuilder(event.getMessage().getEmbeds().getFirst());
            embedBuilder.setColor(Color.GREEN);
            embedBuilder.setFooter("Прийняв: " + event.getUser().getName(), event.getUser().getAvatarUrl());
            discordUser.setWasApproved(true);
            event.getChannel().editMessageEmbedsById(event.getMessageId(), embedBuilder.build()).setComponents().queue();
            Guild guild = event.getGuild();
            if (guild == null) {
                System.out.println("Error: Guild not found!");
                return;
            }

            Member member = guild.retrieveMemberById(discordUser.getDiscordId()).complete();
            if (member == null) {
                System.out.println("Error: Member not found!");
                return;
            }

            String newNickname = discordUser.getNickname();
            member.modifyNickname(newNickname).queueAfter(2, java.util.concurrent.TimeUnit.SECONDS);

            Role roleToRemove = guild.getRoleById(roleToRemoveId);
            if (roleToRemove != null) {
                guild.removeRoleFromMember(member, roleToRemove).queueAfter(3, java.util.concurrent.TimeUnit.SECONDS);
            } else {
                System.out.println("Warning: Role to remove not found!");
            }

            Role roleToAdd = guild.getRoleById(roleToAddId);
            if (roleToAdd != null) {
                guild.addRoleToMember(member, roleToAdd).queueAfter(5, java.util.concurrent.TimeUnit.SECONDS);
            } else {
                System.out.println("Warning: Role to add not found!");
            }

            new ResponseUtil(fallbackChannelId, null).sendSuccessMessage(Long.parseLong(discordUser.getDiscordId()), event.getJDA());

            try (RconClient client = RconClient.open(rconUrl, rconPort, rconPassword)) {
                client.sendCommand("graylist add " + discordUser.getNickname());
            } catch (Exception e) {
                log.error("Error while sending command to Minecraft server", e);
            }

        } else if (componentId.split("-")[0].equals("declined")) {
            EmbedBuilder embedBuilder = new EmbedBuilder(event.getMessage().getEmbeds().getFirst());
            embedBuilder.setColor(Color.RED);
            discordUser.setWasApproved(false);
            embedBuilder.setFooter("Відхилив: " + event.getUser().getName(), event.getUser().getAvatarUrl());
            event.getChannel().editMessageEmbedsById(event.getMessageId(),
                    embedBuilder.build()).setComponents().queue();
            new ResponseUtil(fallbackChannelId, null).sendFailureMessage(Long.parseLong(discordUser.getDiscordId()), event.getJDA());
        }
        discordUserService.update(discordUser);
    }

}
