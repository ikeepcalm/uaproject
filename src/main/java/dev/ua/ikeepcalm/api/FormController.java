package dev.ua.ikeepcalm.api;

import dev.ua.ikeepcalm.data.entities.DiscordUser;
import dev.ua.ikeepcalm.data.services.DiscordUserService;
import dev.ua.ikeepcalm.utils.DiscordParser;
import dev.ua.ikeepcalm.utils.ResponseUtil;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/forms/")
public class FormController {

    @Value("${discord.admin.channel.id}")
    private long adminChannelId;
    private final DiscordUserService discordUserService;
    private final DiscordParser discordParser;
    private final JDA jda;

    public FormController(DiscordUserService discordUserService, DiscordParser discordParser, JDA jda) {
        this.discordUserService = discordUserService;
        this.discordParser = discordParser;
        this.jda = jda;
    }

    @GetMapping("/retrieve")
    public ResponseEntity<Boolean> getDiscordUser(@RequestHeader String discordId) {
        DiscordUser discordUser = discordUserService.findByDiscordId(discordId).orElse(null);
        return ResponseEntity.ok(discordUser != null);
    }

    @GetMapping("/auth")
    public ResponseEntity<AuthResponse> authDiscordUser(@RequestHeader String authCode) {
        try {
            String discordId = discordParser.authDiscordUser(authCode);
            AuthResponse response = new AuthResponse(discordId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyDiscordUser(@RequestHeader String discordId) {
        Optional<DiscordUser> discordUserOptional = discordUserService.findByDiscordId(discordId);
        return discordUserOptional.map(discordUser -> ResponseEntity.ok(!discordUser.isAlreadyTried())).orElseGet(() -> ResponseEntity.ok(true));
    }

    @PostMapping("/submit")
    public ResponseEntity<String> saveDiscordUser(@RequestHeader String discordId, @RequestBody String body) {
        Optional<DiscordUser> discordUserOptional = discordUserService.findByDiscordId(discordId);
        DiscordUser discordUser;
        if (discordUserOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            discordUser = discordUserOptional.get();
        }
        FormWrapper form = FormWrapper.wrapForm(body);
        if (form == null) {
            return ResponseEntity.badRequest().build();
        }
        discordUser.setNickname(form.getNickname());
        discordUser.setBirthday(LocalDate.parse(form.getBirthday()));
        discordUser.setGameLauncher(form.getLauncher());
        discordUser.setTypeOfPlayer(form.getPerson());
        discordUser.setAdvised(form.getSource());
        discordUser.setHobbies(form.getHobbies());
        discordUser.setTask(form.getTasks());
        discordUser.setExperience(form.getExperience());
        discordUser.setConflict(form.getConflicts());
        discordUser.setMemory(form.getMemory());
        discordUser.setAlreadyTried(true);
        discordUserService.update(discordUser);

        new ResponseUtil(null, adminChannelId).sendNewForm(discordUser, jda);

        return ResponseEntity.ok("Form submitted successfully");
    }

    @Setter
    @Getter
    private static class AuthResponse {
        private String discordId;

        public AuthResponse(String discordId) {
            this.discordId = discordId;
        }
    }

}
