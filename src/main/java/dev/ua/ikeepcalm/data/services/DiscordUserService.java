package dev.ua.ikeepcalm.data.services;

import dev.ua.ikeepcalm.data.entities.DiscordUser;
import dev.ua.ikeepcalm.data.repositories.DiscordUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscordUserService {

    private final DiscordUserRepository repository;

    public DiscordUserService(DiscordUserRepository repository) {
        this.repository = repository;
    }

    public Optional<DiscordUser> get(Long id) {
        return repository.findById(id);
    }

    public DiscordUser update(DiscordUser entity) {
        return repository.save(entity);
    }

    public DiscordUser create(DiscordUser entity) {
        return repository.save(entity);
    }

    public Optional<DiscordUser> findByDiscordId(String discordId) {
        return repository.findByDiscordId(discordId);
    }

    public Optional<DiscordUser> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<DiscordUser> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<DiscordUser> list(Pageable pageable, Specification<DiscordUser> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<DiscordUser> findAll() {
        return repository.findAll();
    }

}
