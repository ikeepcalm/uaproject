package dev.ua.ikeepcalm;

import dev.ua.ikeepcalm.bot.EventDispatcher;
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
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration implements WebMvcConfigurer {

    private final List<EventDispatcher> eventDispatchers;
    @Value("${discord.bot-token}")
    private String token;

    public Configuration(List<EventDispatcher> eventDispatchers) {
        this.eventDispatchers = eventDispatchers;
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
                Commands.slash("emporium", "Shows the emporium form"),
                Commands.slash("mirequest", "Request a teleport to the specified coordinates"),
                Commands.slash("arequest", "Request an admin command to be executed"),
                Commands.slash("form", "Shows the form and the donation link").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)),
                Commands.slash("launchers", "Shows information about the launchers"), Commands.slash("eta", "Shows the estimated time of arrival for the server"), Commands.slash("offtop", "Shows the offtop message"), Commands.slash("violation", "Shows the message for reporting violations"), Commands.slash("bugreport", "Shows the message for reporting bugs"), Commands.slash("vote", "Vote for the random user from the same server").addOptions(new OptionData(OptionType.USER, "user", "The user to vote for").setRequired(true)), Commands.slash("results", "Shows the results of the vote").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)), Commands.slash("reset", "Resets the vote results").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)), Commands.slash("graylist", "Updates the server graylist").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)), Commands.slash("sync", "Synchronize linked data").addOptions(new OptionData(OptionType.STRING, "type", "Which data to synchronize").addChoice("Nicknames", "nicknames").addChoice("Roles", "roles").addChoice("Sponsors", "sponsors")).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)), Commands.slash("forgive", "Forgives users with wrong launcher specified").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)), Commands.slash("mute", "Mutes a user").addOptions(new OptionData(OptionType.USER, "user", "The user to mute").setRequired(true), new OptionData(OptionType.STRING, "reason", "The reason for the mute").setRequired(true), new OptionData(OptionType.STRING, "duration", "The duration of the mute, you can use this approach here -> 12d").setRequired(true), new OptionData(OptionType.ATTACHMENT, "proof", "The reason for the mute, now with proof").setRequired(false)).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)), Commands.slash("unmute", "Unmutes a user").addOptions(new OptionData(OptionType.USER, "user", "The user to unmute").setRequired(true)).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS)), Commands.slash("ban", "Bans a user").addOptions(new OptionData(OptionType.USER, "user", "The user to ban").setRequired(true), new OptionData(OptionType.STRING, "reason", "The reason for the ban").setRequired(true), new OptionData(OptionType.STRING, "duration", "The duration of the ban, you can use this approach here -> 365d").setRequired(true), new OptionData(OptionType.ATTACHMENT, "proof", "The reason for the ban, now with proof").setRequired(false)).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS))).queue();
        return jda;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

}
