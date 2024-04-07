package dev.ua.ikeepcalm.views.login;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import dev.ua.ikeepcalm.data.entities.DiscordUser;
import dev.ua.ikeepcalm.data.services.DiscordUserService;
import io.mokulu.discord.oauth.DiscordOAuth;
import io.mokulu.discord.oauth.model.TokensResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@WebServlet(name = "LoginServlet", urlPatterns = "/login/callback")
public class LoginServlet extends HttpServlet {

    @Value("${discord.client-id}")
    private String clientId;
    @Value("${discord.client-secret}")
    private String clientSecret;
    @Value("${discord.redirect-uri}")
    private String redirectUri;
    private final DiscordUserService discordUserService;

    public LoginServlet(String clientId, String clientSecret, String redirectUri, DiscordUserService discordUserService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.discordUserService = discordUserService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String authorizationCode = request.getParameter("code");

        if (authorizationCode != null) {
            String accessToken = getAccessToken(authorizationCode);
            if (accessToken != null) {
                Optional<DiscordUser> user = discordUserService.findByDiscordId(getUserInfo(accessToken).get(0));
                if (user.isPresent()) {
                    discordUserService.update(user.get());
                    String redirectUrl = "/form?" + "key=" + user.get().getDiscordId();
                    response.sendRedirect(request.getContextPath() + redirectUrl);
                } else {
                    if (getUserInfo(accessToken) != null) {
                        List<String> userInfo = getUserInfo(accessToken);
                        DiscordUser newUser = new DiscordUser();
                        newUser.setDiscordId(userInfo.get(0));
                        discordUserService.create(newUser);
                        String redirectUrl = "/form?" + "key=" + newUser.getDiscordId() + "&username=" + userInfo.get(1);
                        response.sendRedirect(request.getContextPath() + redirectUrl);

                    } else {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.getWriter().println("Failed to retrieve user info!");
                    }
                }
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Failed to retrieve access token!");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Missing authorization code!");
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
            return null;
        }
    }
}
