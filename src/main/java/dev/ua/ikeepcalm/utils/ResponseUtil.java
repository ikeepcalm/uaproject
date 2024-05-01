package dev.ua.ikeepcalm.utils;

import dev.ua.ikeepcalm.data.entities.DiscordUser;
import dev.ua.ikeepcalm.views.form.source.LauncherType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.ErrorResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.LocalDate;
import java.time.Period;


public class ResponseUtil {

    private Long fallbackChannelId;
    private Long adminChannelId;

    public ResponseUtil(@Nullable Long fallbackChannelId, @Nullable Long adminChannelId) {
        if (fallbackChannelId == null && adminChannelId == null) {
            throw new IllegalArgumentException("Fallback channel id or admin channel id must be provided.");
        }
        if (fallbackChannelId != null) {
            this.fallbackChannelId = fallbackChannelId;
        }

        if (adminChannelId != null) {
            this.adminChannelId = adminChannelId;
        }
    }

    public void sendNewForm(DiscordUser user, JDA jda) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Нова анкета від - " + user.getUsername());
        embed.setColor(Color.GRAY);
        embed.addField("Нікнейм", user.getNickname(), true);
        long age = getYearsDifference(user.getBirthday(), LocalDate.now());
        if (age > 16) {
            embed.addField("Вік (Рекомендовано)", "Гравцю наразі " + age + " роки(ів)", true);
        } else {
            embed.addField("Вік (Надто молодий)", "Гравцю наразі " + age + " роки(ів)", true);
        }

        LauncherType launcherType = user.getGameLauncher();
        if (launcherType == LauncherType.TLAUNCHER || launcherType == LauncherType.TLAUNCHER_LEGACY || launcherType == LauncherType.KLAUNCHER) {
            embed.addField("Лаунчер (Російський)", launcherType.getLauncher(), true);
        } else {
            embed.addField("Лаунчер (Нормальний)", launcherType.getLauncher(), true);
        }

        embed.addField("Тип гравця", "Гравець найбільше асоціює себе із таким типом - " + user.getTypeOfPlayer().getType(), true);

        if (user.getAdvised().length() > 1024){
            embed.addField("Звідки дізнався про нас", user.getAdvised().substring(0, 1024), true);
        } else {
            embed.addField("Звідки дізнався про нас", user.getAdvised(), true);
        }

        if (user.getHobbies().length() > 1024){
            embed.addField("Чим займається у вільний час", user.getHobbies().substring(0, 1024), false);
        } else {
            embed.addField("Чим займається у вільний час", user.getHobbies(), false);
        }

        if (user.getExperience().length() > 1024){
            embed.addField("Досвід гри на інших серверах", user.getExperience().substring(0, 1024), true);
        } else {
            embed.addField("Досвід гри на інших серверах", user.getExperience(), true);
        }

        if (user.getTask().length() > 1024){
            embed.addField("Задачка зі школи (75 хвилин)", user.getTask().substring(0, 1024), true);
        } else {
            embed.addField("Задачка зі школи (75 хвилин)", user.getTask(), true);
        }

        if (user.getConflict().length() > 1024){
            embed.addField("Конфліктна ситуація", user.getConflict().substring(0, 1024), false);
        } else {
            embed.addField("Конфліктна ситуація", user.getConflict(), false);
        }

        if (user.getMemory().length() > 1024){
            embed.addField("Спогад із дитинства", user.getMemory().substring(0, 1024), false);
        } else {
            embed.addField("Спогад із дитинства", user.getMemory(), false);
        }

        embed.addField("Безпосередньо користувач", "<@" + user.getDiscordId() + ">", true);
        Button approve = Button.primary("approved-" + user.getDiscordId(), "Прийняти");
        Button decline = Button.danger("declined-" + user.getDiscordId(), "Відхилити");
        TextChannel channel = jda.getTextChannelById(adminChannelId);
        channel.sendMessageEmbeds(embed.build())
                .setActionRow(approve, decline)
                .queue();
    }

    public void sendSuccessMessage(long userId, JDA jda) {
        User user = jda.retrieveUserById(userId).complete();
        EmbedBuilder success = getSuccessEmbed();
        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(success.build()))
                .queue(null, new ErrorHandler()
                        .handle(ErrorResponse.CANNOT_SEND_TO_USER,
                                (ex) -> {
                                    long targetChannelId = fallbackChannelId;
                                    TextChannel channel = jda.getTextChannelById(targetChannelId);
                                    if (channel != null) {
                                        EmbedBuilder embed = getSuccessEmbed();
                                        channel.sendMessage(user.getAsMention() + " :)").queue();
                                        channel.sendMessageEmbeds(embed.build()).queue();
                                    } else {
                                        System.out.println("Target channel not found for sending message.");
                                    }
                                }));
    }

    public void sendFailureMessage(long userId, JDA jda) {
        User user = jda.retrieveUserById(userId).complete();
        EmbedBuilder declined = getDeclinedEmbed();
        net.dv8tion.jda.api.interactions.components.buttons.Button buy = net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://donatello.to/fyzzzen?a=50&c=&m=Graylist", "Придбати допуск \uD83D\uDCB5");

        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(declined.build()))
                .queue(null, new ErrorHandler()
                        .handle(ErrorResponse.CANNOT_SEND_TO_USER,
                                (ex) -> {
                                    long targetChannelId = fallbackChannelId;
                                    TextChannel channel = jda.getTextChannelById(targetChannelId);
                                    if (channel != null) {
                                        EmbedBuilder embed = getDeclinedEmbed();
                                        channel.sendMessage(user.getAsMention() + " :)").queue();
                                        channel.sendMessageEmbeds(embed.build())
                                                .setActionRow(buy)
                                                .queue();
                                    } else {
                                        System.out.println("Target channel not found for sending message.");
                                    }
                                }));
    }

    public void sendForgiveMessage(long userId, JDA jda) {
        User user = jda.retrieveUserById(userId).complete();
        EmbedBuilder declined = getForgiveEmbed();
        Button form = Button.link("https://uaproject.xyz/form", "Подати анкету знову \uD83D\uDCB5");

        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(declined.build()))
                .queue(null, new ErrorHandler()
                        .handle(ErrorResponse.CANNOT_SEND_TO_USER,
                                (ex) -> {
                                    long targetChannelId = fallbackChannelId;
                                    TextChannel channel = jda.getTextChannelById(targetChannelId);
                                    if (channel != null) {
                                        EmbedBuilder embed = getForgiveEmbed();
                                        channel.sendMessage(user.getAsMention() + " :)").queue();
                                        channel.sendMessageEmbeds(embed.build())
                                                .setActionRow(form)
                                                .queue();
                                    } else {
                                        System.out.println("Target channel not found for sending message.");
                                    }
                                }));
    }


    private @NotNull EmbedBuilder getSuccessEmbed() {
        EmbedBuilder success = new EmbedBuilder();
        success.setTitle("Результати перевірки анкети");
        success.setColor(Color.GREEN);
        success.setDescription("""
                Вітаю! Ваша анкета щойно була перевірена адміністрацією UaProject.
                                
                З радістю повідомляю, що ви пройшли перевірку, і вашу анкету було прийнято! Якщо ви правильно вказали нікнейм, ви вже можете зайти на сервер за загальним айпі (uaproject.xyz). Якщо ви раніше вже заходили у режимі гостя, не хвилюйтеся, ви вже мали отримати допуск на гру!
                                
                Рекомендую вам ознайомитися з правилами сервера, щоб уникнути непорозумінь. Для гравців вони трохи ширші :'
                                
                Також, якщо у вас виникнуть питання або проблеми, не соромтеся звертатися до адміністрації сервера
                                
                Побачимося по той бік! \uD83D\uDE09
                """);
        success.setImage("https://media.discordapp.net/attachments/1044928852738187318/1226514457136730163/6.png?ex=66250b9a&is=6612969a&hm=504250363b4f12e6cf30bf9e400aef4d6c9b473890c74d6f3a8344974c1e8a0a&=&format=webp&quality=lossless&width=1440&height=450");
        return success;
    }

    private @NotNull EmbedBuilder getForgiveEmbed() {
        EmbedBuilder success = new EmbedBuilder();
        success.setTitle("Другий шанс");
        success.setColor(Color.GREEN);
        success.setDescription("""
                Вітаю! Ви вже мали можливість подати анкету на сервер UaProject, але вибрали неправильний лаунчер.
                                
                На щастя, у вас є ще один шанс! Ви можете виправити помилку, і подати анкету знову.
                                
                Якщо ви вже вирішили проблему з лаунчером, і готові подати анкету, натисніть на кнопку нижче.
                                
                Потрібна допомога? Зверніться у чат глядачів, або до адміністрації сервера. Вам завжди раді допомогти!
                                
                Побачимося на сервері! \uD83D\uDE09
                """);
        return success;
    }

    private @NotNull EmbedBuilder getDeclinedEmbed() {
        EmbedBuilder declined = new EmbedBuilder();
        declined.setTitle("Результати перевірки анкети");
        declined.setColor(Color.RED);
        declined.setDescription("""
                Вітаю! Ваша анкета успішно перевірена адміністрацією сервера UaProject.
                                
                На превеликий жаль, з тих чи інших причин, ви не пройшли перевірку, і вашу анкету було відхилено.
                                
                Найчастіше це стосується недостатньо розгорнутих відповідей, або не відповідності вимогам сервера.
                                
                Також, ви можете бути відхилені за порушення правил сервера - використання заборонених лаунчерів.
                                
                Невже це все? Звісно, ні! Ви досі можете придбати допуск на сервер за 50₴
                                
                Або ж, якщо ви уважно читали інформацію, допуск вам може надати інший гравець серверу
                                
                Побачимося по той бік! \uD83D\uDE09
                """);
        declined.setImage("https://media.discordapp.net/attachments/1143545807451213915/1226512233148711013/4.png?ex=66250987&is=66129487&hm=42811d1352fa24f164aaee677c20537137faa82ce5e04f4beeeb21b6dfa2322d&=&format=webp&quality=lossless&width=687&height=215");
        return declined;
    }

    private long getYearsDifference(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getYears();
    }

}
