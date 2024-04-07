package dev.ua.ikeepcalm.bot.impls;

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
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Optional;

@Component
public class ResponseListener extends ListenerAdapter implements EventDispatcher {

    private final DiscordUserService discordUserService;

    public ResponseListener(DiscordUserService discordUserService) {
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
            System.out.println("Approved");
            EmbedBuilder embedBuilder = new EmbedBuilder(event.getMessage().getEmbeds().getFirst());
            embedBuilder.setColor(Color.GREEN);
            embedBuilder.setFooter("Прийняв: " + event.getUser().getAsTag(), event.getUser().getAvatarUrl());
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
            member.modifyNickname(newNickname).queue();

            long roleToRemoveId = 1221885602690240532L;
            long roleToAddId = 1221552838807654456L;

            Role roleToRemove = guild.getRoleById(roleToRemoveId);
            if (roleToRemove != null) {
                guild.removeRoleFromMember(member, roleToRemove).queue();
            } else {
                System.out.println("Warning: Role to remove not found!");
            }

            Role roleToAdd = guild.getRoleById(roleToAddId);
            if (roleToAdd != null) {
                guild.addRoleToMember(member, roleToAdd).queue();
            } else {
                System.out.println("Warning: Role to add not found!");
            }

            ResponseUtil.sendSuccessMessage(Long.parseLong(discordUser.getDiscordId()), event.getJDA());

            // Add to the graylist

        } else if (componentId.split("-")[0].equals("declined")) {
            System.out.println("Declined");
            EmbedBuilder embedBuilder = new EmbedBuilder(event.getMessage().getEmbeds().getFirst());
            embedBuilder.setColor(Color.RED);
            discordUser.setWasApproved(false);
            embedBuilder.setFooter("Відхилив: " + event.getUser().getAsTag(), event.getUser().getAvatarUrl());
            event.getChannel().editMessageEmbedsById(event.getMessageId(),
                    embedBuilder.build()).setComponents().queue();
            ResponseUtil.sendFailureMessage(Long.parseLong(discordUser.getDiscordId()), event.getJDA());
        } discordUserService.update(discordUser);
    }


}
