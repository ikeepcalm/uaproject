package dev.ua.ikeepcalm.bot.commands;

import dev.ua.ikeepcalm.bot.EventDispatcher;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class EmporiumCommands extends ListenerAdapter implements EventDispatcher {

    @Value("${discord.emporiumChannelId}")
    private long emporiumChannelId;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("emporium")) {
            TextInput nickname = TextInput.create("nickname", "Ваш ігровий нікнейм", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setPlaceholder("Введіть ваш ігровий нікнейм")
                    .build();

            TextInput intValue = TextInput.create("int_value", "Кількість балів", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setPlaceholder("Введіть бажану кількість балів")
                    .build();

            TextInput link = TextInput.create("link", "Посилання", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setPlaceholder("Додайте посилання на медіа-контент")
                    .build();

            Modal modal = Modal.create("emporium", "Емпоріум")
                    .addComponents(
                            ActionRow.of(nickname),
                            ActionRow.of(intValue),
                            ActionRow.of(link)
                    )
                    .build();

            event.replyModal(modal).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("emporium")) {
            String nickname = event.getValue("nickname").getAsString();
            String intValue = event.getValue("int_value").getAsString();
            String link = event.getValue("link").getAsString();
            User user = event.getUser();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Запит на поповнення Емпоріуму - " + user.getName());
            embed.setColor(Color.GRAY);
            embed.addField("Нікнейм", nickname, false);
            embed.addField("Кількість балів", intValue, false);
            embed.addField("Посилання", link, false);

            embed.addField("Безпосередньо користувач", "<@" + user.getId() + ">", true);
            net.dv8tion.jda.api.interactions.components.buttons.Button approve = net.dv8tion.jda.api.interactions.components.buttons.Button.primary("emporium-yes-" + nickname + "-" + intValue, "Підтвердити");
            net.dv8tion.jda.api.interactions.components.buttons.Button decline = Button.danger("emporium-no-" + nickname + "-" + intValue, "Відхилити");
            TextChannel channel = event.getJDA().getTextChannelById(emporiumChannelId);
            channel.sendMessageEmbeds(embed.build())
                    .setActionRow(approve, decline)
                    .queue();
            event.deferReply().queue();
            event.getHook().sendMessage("Запит надіслано!").queue();
        }
    }

}

