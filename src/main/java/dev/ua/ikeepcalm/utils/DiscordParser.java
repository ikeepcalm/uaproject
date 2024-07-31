package dev.ua.ikeepcalm.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.ua.ikeepcalm.data.entities.DiscordUser;
import dev.ua.ikeepcalm.data.services.DiscordUserService;
import io.mokulu.discord.oauth.DiscordOAuth;
import io.mokulu.discord.oauth.model.TokensResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class DiscordParser {

    @Value("${discord.client-id}")
    private String clientId;
    @Value("${discord.client-secret}")
    private String clientSecret;
    @Value("${discord.redirect-uri}")
    private String redirectUri;

    private final DiscordUserService discordUserService;

    public DiscordParser(DiscordUserService discordUserService) {
        this.discordUserService = discordUserService;
    }

    public String authDiscordUser(String authCode) throws IOException {
        try {
            String accessToken = getAccessToken(authCode);
            List<String> userInfo = getUserInfo(accessToken);

            Optional<DiscordUser> user = discordUserService.findByDiscordId(userInfo.getFirst());

            if (user.isPresent()) {
                discordUserService.update(user.get());
                return user.get().getDiscordId();
            }

            DiscordUser newUser = new DiscordUser();
            newUser.setDiscordId(userInfo.getFirst());
            newUser.setUsername(userInfo.get(1));
            newUser.setAlreadyTried(false);
            discordUserService.create(newUser);
            return newUser.getDiscordId();
        } catch (IOException e) {
            throw new IOException("Error while getting user info");
        }
    }

    private String getAccessToken(String authorizationCode) throws IOException {
        DiscordOAuth oauthHandler = new DiscordOAuth(clientId, clientSecret, redirectUri, new String[]{"identify"});
        TokensResponse token = oauthHandler.getTokens(authorizationCode);
        return token.getAccessToken();
    }


    private List<String> getUserInfo(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "https://discord.com/api/users/@me";
        Request request = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + accessToken).build();
        Response userResponse = client.newCall(request).execute();

        if (userResponse.isSuccessful()) {
            String responseBody = userResponse.body().string();
            JsonObject userJson = JsonParser.parseString(responseBody).getAsJsonObject();
            return List.of(userJson.get("id").getAsString(), userJson.get("username").getAsString());
        } else {
            throw new IOException("Error while getting user info");
        }
    }

}
