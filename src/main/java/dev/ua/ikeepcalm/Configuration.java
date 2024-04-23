package dev.ua.ikeepcalm;

import dev.ua.ikeepcalm.bot.EventDispatcher;
import dev.ua.ikeepcalm.data.services.DiscordUserService;
import dev.ua.ikeepcalm.views.login.LoginServlet;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
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
        builder.setActivity(Activity.streaming("UaProject", "https://uaproject.xyz"));
        for (EventDispatcher eventDispatcher : eventDispatchers) {
            builder.addEventListeners(eventDispatcher);
        }

        return builder.build();
    }

}
