package dev.ua.ikeepcalm.data.repositories;


import dev.ua.ikeepcalm.data.entities.DiscordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DiscordUserRepository
        extends
            JpaRepository<DiscordUser, Long>,
            JpaSpecificationExecutor<DiscordUser> {


    Optional<DiscordUser> findByDiscordId(String discordId);
    Optional<DiscordUser> findByUsername(String username);

}
