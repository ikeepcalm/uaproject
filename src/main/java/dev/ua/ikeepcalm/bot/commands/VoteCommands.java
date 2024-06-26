package dev.ua.ikeepcalm.bot.commands;

import dev.ua.ikeepcalm.bot.EventDispatcher;
import dev.ua.ikeepcalm.data.entities.vote.UserVote;
import dev.ua.ikeepcalm.data.entities.vote.Vote;
import dev.ua.ikeepcalm.data.repositories.UserVoteRepository;
import dev.ua.ikeepcalm.data.repositories.VoteRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class VoteCommands extends ListenerAdapter implements EventDispatcher {

    private final VoteRepository voteRepository;
    private final UserVoteRepository userVoteRepository;

    public VoteCommands(VoteRepository voteRepository, UserVoteRepository userVoteRepository) {
        this.voteRepository = voteRepository;
        this.userVoteRepository = userVoteRepository;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "vote" -> handleVoteCommand(event);
            case "results" -> handleResultsCommand(event);
            case "reset" -> handlePurgeVotesCommand(event);
        }
    }

    private void handleVoteCommand(SlashCommandInteractionEvent event) {
        String voterId = event.getUser().getId();
        String voteeId = event.getOption("user").getAsUser().getId();

        if (userVoteRepository.existsById(voterId)) {
            event.reply("Ви вже голосували у цьому наборі!").setEphemeral(true).queue();
            return;
        }

        Vote vote = voteRepository.findById(voteeId).orElse(new Vote());
        vote.setUserId(voteeId);
        vote.setCount(vote.getCount() + 1);
        voteRepository.save(vote);

        UserVote userVote = new UserVote();
        userVote.setVoterId(voterId);
        userVote.setVoteeId(voteeId);
        userVoteRepository.save(userVote);

        event.reply("Ваш голос було зараховано!").queue();
    }

    private void handleResultsCommand(SlashCommandInteractionEvent event) {
        StringBuilder results = new StringBuilder("Результати голосування:\n");
        voteRepository.findAll().forEach(vote -> {
            results.append("<@").append(vote.getUserId()).append(">: ").append(vote.getCount()).append(" голосів\n");
        });
        event.reply(results.toString()).queue();
    }

    private void handlePurgeVotesCommand(SlashCommandInteractionEvent event) {
        voteRepository.deleteAll();
        userVoteRepository.deleteAll();
        event.reply("Всі дані про голосування були видалені!").queue();
    }
}
