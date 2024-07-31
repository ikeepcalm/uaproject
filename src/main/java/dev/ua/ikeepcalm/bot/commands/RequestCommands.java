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
public class RequestCommands extends ListenerAdapter implements EventDispatcher {

    @Value("${discord.request-channel-id}")
    private long requestChannelId;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("mirequest")) {
            TextInput nickname = TextInput.create("nickname", "Ваш ігровий нікнейм", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setPlaceholder("Введіть ваш ігровий нікнейм")
                    .build();

            TextInput coordinates = TextInput.create("coordinates", "Координати (Х У Z)", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setPlaceholder("Введіть координати для телепорту")
                    .build();

            TextInput proof = TextInput.create("proof", "Мотивація", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setPlaceholder("Мотивація для телепорту. Якщо є номер тікету, то його #айді")
                    .build();

            Modal modal = Modal.create("mirequest", "Поліцейський запит")
                    .addComponents(
                            ActionRow.of(nickname),
                            ActionRow.of(coordinates),
                            ActionRow.of(proof))
                    .build();

            event.replyModal(modal).queue();
        } else if (event.getName().equals("arequest")) {
            TextInput command = TextInput.create("command", "Команда для виконання", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setPlaceholder("Введіть координати для телепорту")
                    .build();

            TextInput proof = TextInput.create("proof", "Мотивація", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setPlaceholder("Мотивація для виконання команди. Якщо є номер тікету, то його #айді")
                    .build();

            Modal modal = Modal.create("arequest", "Адміністраторський запит")
                    .addComponents(
                            ActionRow.of(command),
                            ActionRow.of(proof)
                    )
                    .build();

            event.replyModal(modal).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("mirequest")) {
            String nickname = event.getValue("nickname").getAsString();
            String coordinates = event.getValue("coordinates").getAsString();
            String proof = event.getValue("proof").getAsString();
            User user = event.getUser();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Запит на поповнення Емпоріуму - " + user.getName());
            embed.setColor(Color.GRAY);
            embed.addField("Нікнейм", nickname, false);
            embed.addField("Координати", coordinates, false);
            embed.addField("Пояснення", proof, false);

            embed.addField("Безпосередньо користувач", "<@" + user.getId() + ">", true);
            net.dv8tion.jda.api.interactions.components.buttons.Button approve = net.dv8tion.jda.api.interactions.components.buttons.Button.primary("mirequest-yes-" + nickname + "-" + coordinates, "Підтвердити");
            net.dv8tion.jda.api.interactions.components.buttons.Button decline = Button.danger("mirequest-no-" + nickname + "-" + coordinates, "Відхилити");
            TextChannel channel = event.getJDA().getTextChannelById(requestChannelId);
            channel.sendMessageEmbeds(embed.build())
                    .setActionRow(approve, decline)
                    .queue();
            event.deferReply().queue();
            event.getHook().sendMessage("Запит надіслано!").queue();
        } else if (event.getModalId().equals("arequest")) {
            String command = event.getValue("command").getAsString();
            String proof = event.getValue("proof").getAsString();
            User user = event.getUser();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Запит на виконання команди - " + user.getName());
            embed.setColor(Color.GRAY);
            embed.addField("Команда", command, false);
            embed.addField("Пояснення", proof, false);

            embed.addField("Безпосередньо користувач", "<@" + user.getId() + ">", true);
            net.dv8tion.jda.api.interactions.components.buttons.Button approve = net.dv8tion.jda.api.interactions.components.buttons.Button.primary("arequest-yes-" + command, "Підтвердити");
            net.dv8tion.jda.api.interactions.components.buttons.Button decline = Button.danger("arequest-no-" + command, "Відхилити");
            TextChannel channel = event.getJDA().getTextChannelById(requestChannelId);
            channel.sendMessageEmbeds(embed.build())
                    .setActionRow(approve, decline)
                    .queue();
            event.deferReply().queue();
            event.getHook().sendMessage("Запит надіслано!").queue();
        }
    }
}
