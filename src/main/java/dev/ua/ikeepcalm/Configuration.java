package dev.ua.ikeepcalm;

import dev.ua.ikeepcalm.bot.EventDispatcher;
import dev.ua.ikeepcalm.data.services.DiscordUserService;
import dev.ua.ikeepcalm.views.login.LoginServlet;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${discord.client-id}")
    private String clientId;
    @Value("${discord.client-secret}")
    private String clientSecret;
    @Value("${discord.redirect-uri}")
    private String redirectUri;
    @Value("${discord.bot-token}")
    private String token;

    private final List<EventDispatcher> eventDispatchers;

    private final DiscordUserService discordUserService;

    public Configuration(DiscordUserService discordUserService, List<EventDispatcher> eventDispatchers) {
        this.discordUserService = discordUserService;
        this.eventDispatchers = eventDispatchers;
    }


    @Bean
    public ServletRegistrationBean<LoginServlet> loginServletRegistrationBean() {
        ServletRegistrationBean<LoginServlet> bean = new ServletRegistrationBean<>(
                new LoginServlet(clientId, clientSecret, redirectUri, discordUserService),
                "/login/callback"
        );
        bean.setLoadOnStartup(1);
        return bean;
    }

    @Bean
    public JDA jdaRegistrationBean() {
        JDABuilder builder = JDABuilder.createDefault(token, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MODERATION, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.streaming("UAProject", "https://uaproject.xyz"));

        for (EventDispatcher eventDispatcher : eventDispatchers) {
            builder.addEventListeners(eventDispatcher);
        }

        JDA jda = builder.build();
        jda.updateCommands().addCommands(
                Commands.slash("ping", "Replies with a pong!"),
                Commands.slash("form", "Shows the form and the donation link").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)),
                Commands.slash("launchers", "Shows information about the launchers"),
                Commands.slash("eta", "Shows the estimated time of arrival for the server"),
                Commands.slash("offtop", "Shows the offtop message"),
                Commands.slash("violation", "Shows the message for reporting violations"),
                Commands.slash("graylist", "Updates the server graylist")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)),
                Commands.slash("sync", "Synchronize linked data").addOptions(
                        new OptionData(OptionType.STRING, "type", "Which data to synchronize")
                                .addChoice("Nicknames", "nicknames")
                                .addChoice("Roles", "roles")
                                .addChoice("Sponsors", "sponsors")).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)),
                Commands.slash("forgive", "Forgives users with wrong launcher specified").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)),
                Commands.slash("mute", "Mutes a user").addOptions(
                        new OptionData(OptionType.USER, "user", "The user to mute").setRequired(true),
                        new OptionData(OptionType.STRING, "reason", "The reason for the mute").setRequired(true),
                        new OptionData(OptionType.STRING, "duration", "The duration of the mute, you can use this approach here -> 12d").setRequired(true),
                        new OptionData(OptionType.ATTACHMENT, "proof", "The reason for the mute, now with proof").setRequired(false)
                ).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)),
                Commands.slash("unmute", "Unmutes a user").addOptions(
                        new OptionData(OptionType.USER, "user", "The user to unmute").setRequired(true)
                ).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)),
                Commands.slash("ban", "Bans a user").addOptions(
                        new OptionData(OptionType.USER, "user", "The user to ban").setRequired(true),
                        new OptionData(OptionType.STRING, "reason", "The reason for the ban").setRequired(true),
                        new OptionData(OptionType.STRING, "duration", "The duration of the ban, you can use this approach here -> 365d").setRequired(true),
                        new OptionData(OptionType.ATTACHMENT, "proof", "The reason for the ban, now with proof").setRequired(false)
                ).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS))
        ).queue();
        return jda;
    }

}
