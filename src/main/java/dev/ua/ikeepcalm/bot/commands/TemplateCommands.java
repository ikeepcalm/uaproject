package dev.ua.ikeepcalm.bot.commands;

import dev.ua.ikeepcalm.bot.EventDispatcher;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.stereotype.Component;

@Component
public class TemplateCommands extends ListenerAdapter implements EventDispatcher {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "ping" -> event.reply("Pong!").queue();

            case "form" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.utils.EmbedBuilder.getFormEmbed();
                Button buy = Button.link("https://donatello.to/fyzzzen?a=50&c=&m=Graylist", "Придбати допуск \uD83D\uDCB5");
                Button form = Button.link("https://uaproject.xyz/form", "Заповнити анкету \uD83D\uDCDD");
                event.replyEmbeds(embed.build())
                        .addActionRow(buy, form)
                        .queue();
            }

            case "launchers" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.utils.EmbedBuilder.getLaunchersEmbed();
                Button polymc = Button.link("https://github.com/fn2006/PollyMC/releases/download/8.0/PollyMC-Windows-MSVC-Setup-8.0.exe", "PollyMc");
                Button sklauncher = Button.link("https://skmedix.pl", "SKLauncher");
                event.replyEmbeds(embed.build())
                        .addActionRow(polymc, sklauncher)
                        .queue();

            }

            case "eta" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.utils.EmbedBuilder.getEstimatedArrivalTimeEmbed();
                event.replyEmbeds(embed.build()).queue();
            }

            case "bugreport" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.utils.EmbedBuilder.getBugReportEmbed();
                event.replyEmbeds(embed.build()).queue();
            }

            case "offtop" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.utils.EmbedBuilder.getOfftopEmbed();
                event.replyEmbeds(embed.build()).queue();
            }

            case "violation" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.utils.EmbedBuilder.getMi9HelpEmbed();
                event.replyEmbeds(embed.build()).queue();
            }
        }
    }
}

