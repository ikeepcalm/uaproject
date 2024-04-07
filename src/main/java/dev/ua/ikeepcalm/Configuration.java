package dev.ua.ikeepcalm;

import dev.ua.ikeepcalm.data.services.DiscordUserService;
import dev.ua.ikeepcalm.views.login.LoginServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${discord.client-id}")
    private String clientId;
    @Value("${discord.client-secret}")
    private String clientSecret;
    @Value("${discord.redirect-uri}")
    private String redirectUri;

    private final DiscordUserService discordUserService;

    public Configuration(DiscordUserService discordUserService) {
        this.discordUserService = discordUserService;
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
}
