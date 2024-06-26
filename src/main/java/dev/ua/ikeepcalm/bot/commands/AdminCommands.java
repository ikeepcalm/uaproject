package dev.ua.ikeepcalm.bot.commands;

import dev.ua.ikeepcalm.bot.EventDispatcher;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class AdminCommands extends ListenerAdapter implements EventDispatcher {

    @Value("${discord.journal-channel-id}")
    private long journalChannelId;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "mute" -> {
                event.deferReply().queue();
                Member member = Objects.requireNonNull(event.getOption("user")).getAsMember();
                String reason = Objects.requireNonNull(event.getOption("reason")).getAsString();
                long minutes;
                try {
                    minutes = parseDuration(event.getOption("duration").getAsString());
                } catch (NumberFormatException e) {
                    event.getHook().sendMessage("Invalid duration! Consider using d, m, s!").queue();
                    return;
                }

                Objects.requireNonNull(event.getGuild()).timeoutFor(member, minutes, TimeUnit.MINUTES).reason(reason).queue();
                event.getHook().sendMessage("User was muted!").queue();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Доказ муту");
                embed.setColor(Color.RED);
                embed.addField("Модератор", event.getUser().getAsMention(), true);
                embed.addField("Користувач", member.getAsMention(), true);
                embed.addField("Причина", reason, true);
                embed.addField("Тривалість", event.getOption("duration").getAsString(), true);
                if (event.getOption("proof") != null) {
                    embed.setImage(event.getOption("proof").getAsAttachment().getUrl());
                }
                TextChannel channel = event.getGuild().getTextChannelById(journalChannelId);
                if (channel != null) {
                    channel.sendMessageEmbeds(embed.build()).queue();
                }
            }

            case "unmute" -> {
                event.deferReply().queue();
                Member member = Objects.requireNonNull(event.getOption("user")).getAsMember();
                Objects.requireNonNull(event.getGuild()).removeTimeout(member).queue();
                event.getHook().sendMessage("User was unmuted!").queue();
            }

            case "ban" -> {
                event.deferReply().queue();
                Member member = Objects.requireNonNull(event.getOption("user")).getAsMember();
                String reason = Objects.requireNonNull(event.getOption("reason")).getAsString();
                int minutes;

                try {
                    minutes = Integer.parseInt(event.getOption("duration").getAsString());
                } catch (NumberFormatException e) {
                    event.getHook().sendMessage("Invalid duration! Consider using d, m, s!").queue();
                    return;
                }

                Objects.requireNonNull(event.getGuild()).ban(member, minutes, TimeUnit.MINUTES).queue();
                event.getHook().sendMessage("User was banned!").queue();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Доказ бану");
                embed.setColor(Color.RED);
                embed.addField("Модератор", event.getUser().getAsMention(), true);
                embed.addField("Користувач", member.getAsMention(), true);
                embed.addField("Причина", reason, true);
                embed.addField("Тривалість", event.getOption("duration").getAsString(), true);
                if (event.getOption("proof") != null) {
                    embed.setImage(event.getOption("proof").getAsAttachment().getUrl());
                }
                TextChannel channel = event.getGuild().getTextChannelById(journalChannelId);
                if (channel != null) {
                    channel.sendMessageEmbeds(embed.build()).queue();
                }
            }
        }
    }

    private static long parseDuration(String duration) throws NumberFormatException {
        String[] units = duration.split(" ");
        long totalMinutes = 0;

        for (String unit : units) {
            char type = unit.charAt(unit.length() - 1);
            int value = Integer.parseInt(unit.substring(0, unit.length() - 1));

            switch (type) {
                case 'd':
                    totalMinutes += value * 24L * 60L;
                    break;
                case 'h':
                    totalMinutes += value * 60L;
                    break;
                case 'm':
                    totalMinutes += value;
                    break;
                case 's':
                    totalMinutes += value / 60;
                    break;
            }
        }

        return totalMinutes;
    }

}

