package dev.ua.ikeepcalm.bot.impls;

import com.google.gson.Gson;
import dev.ua.ikeepcalm.bot.EventDispatcher;
import dev.ua.ikeepcalm.data.entities.DiscordUser;
import dev.ua.ikeepcalm.data.entities.donatello.Donatello;
import dev.ua.ikeepcalm.data.entities.donatello.Donation;
import dev.ua.ikeepcalm.data.services.DiscordUserService;
import dev.ua.ikeepcalm.utils.ResponseUtil;
import dev.ua.ikeepcalm.views.form.source.LauncherType;
import io.graversen.minecraft.rcon.MinecraftRcon;
import io.graversen.minecraft.rcon.service.ConnectOptions;
import io.graversen.minecraft.rcon.service.MinecraftRconService;
import io.graversen.minecraft.rcon.service.RconDetails;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class CommandsListener extends ListenerAdapter implements EventDispatcher {

    private final DiscordUserService discordUserService;

    @Value("${donatello.token}")
    private String token;

    @Value("${discord.fallback-channel-id}")
    private long fallbackChannelId;

    @Value("${discord.journal-channel-id}")
    private long journalChannelId;

    @Value("${minecraft.rcon}")
    private String rconUrl;

    @Value("${minecraft.port}")
    private Integer rconPort;

    @Value("${minecraft.password}")
    private String rconPassword;

    public CommandsListener(DiscordUserService discordUserService) {
        this.discordUserService = discordUserService;
    }


    private Donatello performRequest() throws URISyntaxException, IOException, InterruptedException {
        URI url = new URI("https://donatello.to/api/v1/donates");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("X-Token", token)
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        return gson.fromJson(response.body(), Donatello.class);
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

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "ping" -> event.reply("Pong!").queue();

            case "form" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.impls.EmbedBuilder.getFormEmbed();
                Button buy = Button.link("https://donatello.to/fyzzzen?a=50&c=&m=Graylist", "Придбати допуск \uD83D\uDCB5");
                Button form = Button.link("https://uaproject.xyz/form", "Заповнити анкету \uD83D\uDCDD");
                event.replyEmbeds(embed.build())
                        .addActionRow(buy, form)
                        .queue();
            }

            case "launchers" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.impls.EmbedBuilder.getLaunchersEmbed();
                Button polymc = Button.link("https://github.com/fn2006/PollyMC/releases/download/8.0/PollyMC-Windows-MSVC-Setup-8.0.exe", "PollyMc");
                Button sklauncher = Button.link("https://skmedix.pl", "SKLauncher");
                event.replyEmbeds(embed.build())
                        .addActionRow(polymc, sklauncher)
                        .queue();

            }

            case "eta" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.impls.EmbedBuilder.getEstimatedArrivalTimeEmbed();
                event.replyEmbeds(embed.build()).queue();
            }

            case "offtop" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.impls.EmbedBuilder.getOfftopEmbed();
                event.replyEmbeds(embed.build()).queue();
            }

            case "violation" -> {
                EmbedBuilder embed = dev.ua.ikeepcalm.bot.impls.EmbedBuilder.getMi9HelpEmbed();
                event.replyEmbeds(embed.build()).queue();
            }

            case "graylist" -> {
                event.deferReply().queue();
                List<DiscordUser> users = discordUserService.findAll();
                final MinecraftRconService minecraftRconService = new MinecraftRconService(
                        new RconDetails(rconUrl, rconPort, rconPassword),
                        ConnectOptions.defaults()
                );

                for (DiscordUser user : users) {
                    if (user.isWasApproved()) {
                        minecraftRconService.connectBlocking(Duration.ofSeconds(5));
                        MinecraftRcon minecraftRcon = minecraftRconService.minecraftRcon().orElseThrow(IllegalStateException::new);
                        minecraftRcon.sendAsync(() -> "graylist add " + user.getNickname());
                    }
                }
                event.getHook().sendMessage("Graylist updated!").queue();
            }

            case "forgive" -> {
                event.deferReply().queue();
                List<DiscordUser> users = discordUserService.findAll();
                users.forEach(user -> {
                    if (!user.isWasApproved() && (user.getGameLauncher() == LauncherType.TLAUNCHER || user.getGameLauncher() == LauncherType.TLAUNCHER_LEGACY || user.getGameLauncher() == LauncherType.KLAUNCHER)) {
                        Guild guild = event.getGuild();
                        Member member;
                        try {
                            member = guild.retrieveMemberById(user.getDiscordId()).complete();
                            new ResponseUtil(fallbackChannelId, null).sendForgiveMessage(member.getIdLong(), event.getJDA());
                        } catch (ErrorResponseException ignored) {
                        }
                        discordUserService.delete(user.getId());
                    }
                });
                event.getHook().sendMessage("Users with wrong launcher specified have been forgiven!").queue();
            }

            case "sync" -> event.getOption("type", e -> {
                event.deferReply().queue();
                switch (e.getAsString()) {
                    case "roles" -> {
                        List<DiscordUser> users = discordUserService.findAll();
                        users.forEach(user -> {
                            if (user.isWasApproved()) {
                                Guild guild = event.getGuild();
                                if (guild == null) {
                                    return;
                                }
                                try {
                                    Member member = guild.retrieveMemberById(user.getDiscordId()).complete();
                                    guild.addRoleToMember(member, Objects.requireNonNull(guild.getRoleById(1221552838807654456L))).queueAfter(2, java.util.concurrent.TimeUnit.SECONDS);
                                    guild.removeRoleFromMember(member, Objects.requireNonNull(guild.getRoleById(1221885602690240532L))).queueAfter(2, java.util.concurrent.TimeUnit.SECONDS);
                                } catch (ErrorResponseException ignored) {
                                }
                            }
                        });
                        event.getHook().sendMessage("Roles synced!").queue();
                    }
                    case "nicknames" -> {
                        List<DiscordUser> users = discordUserService.findAll();
                        users.forEach(user -> {
                            if (user.isWasApproved()) {
                                Guild guild = event.getGuild();
                                if (guild == null) {
                                    return;
                                }
                                try {
                                    Member member = guild.retrieveMemberById(user.getDiscordId()).complete();
                                    event.getGuild().modifyNickname(member, user.getNickname()).queueAfter(2, java.util.concurrent.TimeUnit.SECONDS);
                                } catch (ErrorResponseException ignored) {
                                }
                            }
                        });
                        event.getHook().sendMessage("Nicknames synced!").queue();
                    }
                    case "sponsors" -> {
                        try {
                            Donatello donatello = performRequest();
                            for (Donation donation : donatello.getContent()) {
                                int amount = Integer.parseInt(donation.getAmount());
                                if (donation.getMessage().contains("Graylist")) {
                                    if (amount >= 50) {
                                        Optional<DiscordUser> user = discordUserService.findByUsername(donation.getClientName());
                                        if (user.isPresent() && !user.get().isWasApproved()) {
                                            DiscordUser discordUser = user.get();
                                            discordUser.setWasApproved(true);
                                            discordUserService.update(discordUser);
                                        }
                                        List<Member> member = Objects.requireNonNull(event.getGuild()).getMembersByNickname(donation.getClientName(), true);
                                        if (!member.isEmpty()) {
                                            Member sponsor = member.getFirst();
                                            event.getGuild().addRoleToMember(sponsor, Objects.requireNonNull(event.getGuild().getRoleById(1221552838807654456L))).queueAfter(2, java.util.concurrent.TimeUnit.SECONDS);
                                            event.getGuild().removeRoleFromMember(sponsor, Objects.requireNonNull(event.getGuild().getRoleById(1221885602690240532L))).queueAfter(2, java.util.concurrent.TimeUnit.SECONDS);
                                        }
                                    }
                                }
                            }
                            event.getHook().sendMessage("Sponsors synced!").queue();
                        } catch (URISyntaxException | IOException | InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    default -> event.getHook().sendMessage("Invalid type!").queue();
                }
                return true;
            });

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

            default -> event.reply("Invalid command!").queue();
        }
    }
}

