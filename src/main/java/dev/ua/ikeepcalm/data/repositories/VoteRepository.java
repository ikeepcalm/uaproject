package dev.ua.ikeepcalm.data.repositories;

import dev.ua.ikeepcalm.data.entities.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, String> {
}
