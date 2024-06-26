package dev.ua.ikeepcalm.data.repositories;

import dev.ua.ikeepcalm.data.entities.vote.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoteRepository extends JpaRepository<UserVote, String> {
}