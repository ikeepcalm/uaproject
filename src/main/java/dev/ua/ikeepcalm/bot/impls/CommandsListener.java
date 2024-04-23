package dev.ua.ikeepcalm.bot.impls;

import dev.ua.ikeepcalm.bot.EventDispatcher;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class CommandsListener extends ListenerAdapter implements EventDispatcher {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if (event.getMessage().getContentDisplay().isEmpty() || !event.getMessage().getContentDisplay().startsWith("!")) {
            return;
        }

        switch (event.getMessage().getContentDisplay()) {
            case "!ping":
                long time = System.currentTimeMillis();
                event.getChannel().sendMessage("Pong!")
                        .queue(response -> response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue());
                break;
            case "!form":
                EmbedBuilder embed = getEmbedBuilder();
                Button buy = Button.link("https://donatello.to/fyzzzen", "Придбати допуск \uD83D\uDCB5");
                Button form = Button.link("https://uaproject.xyz/form", "Заповнити анкету \uD83D\uDCDD");

                event.getChannel().sendMessageEmbeds(embed.build())
                        .addActionRow(buy, form)
                        .queue();
        }
    }

    @NotNull
    private static EmbedBuilder getEmbedBuilder() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Як пройти на сервер?");
        embed.setColor(Color.YELLOW);
        embed.setDescription("Вітаю, поціновувач майнкрафту! Для того, щоб отримати прохідку (дозвіл грати на нашому сервері) і \n" +
                "вже безпосередньо зайти на нього за загальним айпі (uaproject.xyz), у тебе є два шляхи:");
        embed.addField("1. Придбати допуск за 50₴", "Все, що вам треба зробити для цього, натиснути на відповідну кнопку під цим повідомленням, вказати у полі Ім'я ваш дійсний ігровий нікнейм, обрати суму, що дорівнює, або перевищує 50₴. Ваш нікнейм буде додано до білого списку серверу майже моментально.", false);
        embed.addField("2. Заповнити анкету", "Для того, щоб заповнити анкету вам потрібно натиснути на кнопку під цим повідомленням, уважно прочитати кожне питання, написати розгорнуту відповідь, надіслати, і чекати, поки адміністрація перевірить її. Результат анкети ви отримаєте у приватних повідомленнях. Уважно передивіться правила перед цим варіантом! У вас є лише одна спроба.", false);
        embed.setImage("https://media.discordapp.net/attachments/1155135359265546392/1155135375329738824/vitania.png?ex=6623c4a6&is=66114fa6&hm=cb1f02603525bc12e4f2db9d72e34da2816534b197e4bae9ddef2acd50a7aea4&=&format=webp&quality=lossless&width=1440&height=450");
        return embed;
    }

}
