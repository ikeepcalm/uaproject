package dev.ua.ikeepcalm.bot.commands;

import com.github.t9t.minecraftrconclient.RconClient;
import com.google.gson.Gson;
import dev.ua.ikeepcalm.bot.EventDispatcher;
import dev.ua.ikeepcalm.data.entities.DiscordUser;
import dev.ua.ikeepcalm.data.entities.donatello.Donatello;
import dev.ua.ikeepcalm.data.entities.donatello.Donation;
import dev.ua.ikeepcalm.data.services.DiscordUserService;
import dev.ua.ikeepcalm.utils.ResponseUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class MinecraftCommands extends ListenerAdapter implements EventDispatcher {

    private final DiscordUserService discordUserService;

    @Value("${donatello.token}")
    private String token;

    @Value("${discord.fallback-channel-id}")
    private long fallbackChannelId;

    @Value("${minecraft.rcon}")
    private String rconUrl;

    @Value("${minecraft.port}")
    private Integer rconPort;

    @Value("${minecraft.password}")
    private String rconPassword;

    public MinecraftCommands(DiscordUserService discordUserService) {
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

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "graylist" -> {
                event.deferReply().queue();
                List<DiscordUser> users = discordUserService.findAll();

                for (DiscordUser user : users) {
                    if (user.isWasApproved()) {
                        try (RconClient client = RconClient.open(rconUrl, rconPort, rconPassword)) {
                            client.sendCommand("graylist add " + user.getNickname());
                        }
                    }
                }
                event.getHook().sendMessage("Graylist updated!").queue();
            }

            case "forgive" -> {
                event.deferReply().queue();
                List<DiscordUser> users = discordUserService.findAll();
                users.forEach(user -> {
                    if (!user.isWasApproved() && (user.getGameLauncher().contains("tlauncher"))) {
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
        }
    }
}

