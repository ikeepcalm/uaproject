package dev.ua.ikeepcalm.utils;

import dev.ua.ikeepcalm.data.entities.DiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.time.LocalDate;
import java.time.Period;


public class ResponseUtil {

    public static void sendFailureMessage(long userId, JDA jda) {
        jda.retrieveUserById(userId).queue(user -> user.openPrivateChannel().queue(channel -> {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Результати перевірки анкети");
            embed.setColor(Color.RED);
            embed.setDescription("""
                    Вітаю! Ваша анкета успішно перевірена адміністрацією сервера UaProject.
                                            
                    На превеликий жаль, з тих чи інших причин, ви не пройшли перевірку, і вашу анкету було відхилено.
                    
                    Найчастіше це стосується недостатньо розгорнутих відповідей, або не відповідності вимогам сервера.
                    
                    Також, ви можете бути відхилені за порушення правил сервера - використання заборонених лаунчерів.
                                            
                    Невже це все? Звісно, ні! Ви досі можете придбати допуск на сервер за 50₴ ⬇️
                    Або ж, якщо ви уважно читали інформацію, допуск вам може надати інший гравець серверу
                                            
                    Побачимося по той бік! \uD83D\uDE09
                    """);
            embed.setImage("https://media.discordapp.net/attachments/1143545807451213915/1226512233148711013/4.png?ex=66250987&is=66129487&hm=42811d1352fa24f164aaee677c20537137faa82ce5e04f4beeeb21b6dfa2322d&=&format=webp&quality=lossless&width=687&height=215");
            net.dv8tion.jda.api.interactions.components.buttons.Button buy = net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://donatello.to/fyzzzen", "Придбати допуск \uD83D\uDCB5");
            user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessageEmbeds(embed.build())
                    .setActionRow(buy)
                    .queue());
        }, failure -> {
            long targetChannelId = 1221552839181078608L;
            TextChannel channel = jda.getTextChannelById(targetChannelId);
            if (channel != null) {
                channel.sendMessage("Could not send private message to " + user.getName());
            } else {
                System.out.println("Target channel not found for sending message.");
            }
        }));
    }

    public static void sendSuccessMessage(long userId, JDA jda) {
        jda.retrieveUserById(userId).queue(user -> user.openPrivateChannel().queue(channel -> {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Результати перевірки анкети");
            embed.setColor(Color.GREEN);
            embed.setDescription("""
                    Вітаю! Ваша анкета щойно була перевірена адміністрацією UaProject.
                                            
                    З радістю повідомляю, що ви пройшли перевірку, і вашу анкету було прийнято! Якщо ви правильно вказали нікнейм, ви вже можете зайти на сервер за загальним айпі (uaproject.xyz). Якщо ви раніше вже заходили у режимі гостя, не хвилюйтеся, ви вже мали отримати допуск на гру!
                                            
                    Рекомендую вам ознайомитися з правилами сервера, щоб уникнути непорозумінь. Для гравців вони трохи ширші :'
                    Також, якщо у вас виникнуть питання або проблеми, не соромтеся звертатися до адміністрації сервера
                                            
                    Побачимося по той бік! \uD83D\uDE09
                    """);
            embed.setImage("https://media.discordapp.net/attachments/1044928852738187318/1226514457136730163/6.png?ex=66250b9a&is=6612969a&hm=504250363b4f12e6cf30bf9e400aef4d6c9b473890c74d6f3a8344974c1e8a0a&=&format=webp&quality=lossless&width=1440&height=450");
            user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessageEmbeds(embed.build())
                    .queue());
        }, failure -> {
            long targetChannelId = 1221552839181078608L;
            TextChannel channel = jda.getTextChannelById(targetChannelId);
            if (channel != null) {
                channel.sendMessage("Could not send private message to " + user.getName());
            } else {
                System.out.println("Target channel not found for sending message.");
            }
        }));
    }

    public static void sendNewForm(DiscordUser user, JDA jda) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Нова анкета від - " + user.getUsername());
        embed.setColor(Color.GRAY);
        embed.addField("Ігровий нікнейм", user.getNickname(), true);
        embed.addField("Дата народження + вік", user.getBirthday() + " " + getYearsDifference(LocalDate.now(), user.getBirthday()), true);
        embed.addField("Ігровий лаунчер", user.getGameLauncher().getLauncher(), true);
        embed.addField("Тип гравця", user.getTypeOfPlayer().getType(), true);
        embed.addField("Звідки дізнався про нас", user.getAdvised(), true);
        embed.addField("Хоббі у вільний час", user.getHobbies(), true);
        embed.addField("Досвід гри на серверах", user.getExperience(), true);
        embed.addField("Дуже складна задачка", user.getTask(), true);
        embed.addField("Конфліктна ситуація", user.getConflict(), true);
        embed.addField("Спогад із дитинства", user.getMemory(), true);
        embed.addField("Безпосередньо користувач", "<@" + user.getDiscordId() + ">", true);
        Button approve = Button.primary("approved-" + user.getDiscordId(), "Прийняти");
        Button decline = Button.danger("declined-" + user.getDiscordId(), "Відхилити");
        TextChannel channel = jda.getTextChannelById(1221562100409962538L);
        channel.sendMessageEmbeds(embed.build())
                .setActionRow(approve, decline)
                .queue();
    }

    private static long getYearsDifference(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getYears();
    }

}
