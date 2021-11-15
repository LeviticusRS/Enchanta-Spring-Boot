package com.joshua.ransom.controller;

import com.joshua.ransom.model.Account;
import com.joshua.ransom.model.Vote;
import com.joshua.ransom.util.Sessions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


@Controller
@Slf4j
public class VoteController {

    private final JdbcTemplate db;

    public VoteController(JdbcTemplate db) {
        this.db = db;
    }

    @RequestMapping(value = "/vote")
    public String vote(Model model) {
        if (!Sessions.has()) {
            return "redirect:/login";
        }

        Account account = Account.populateView(db);

        // Block missing email
        if (account.getEmail() == null || account.getEmail().equals("invalid@email.com")) {
            return "redirect:/email";
        }
        model.addAttribute("topVoters", topVoters.get());
        model.addAttribute("runelocusTime", Vote.getMostRecentCompletedVote(db, account.id(), 101));
        model.addAttribute("top100Time", Vote.getMostRecentCompletedVote(db, account.id(), 102));
        model.addAttribute("rspsListTime", Vote.getMostRecentCompletedVote(db, account.id(), 103));
        model.addAttribute("topGTime", Vote.getMostRecentCompletedVote(db, account.id(), 105));
        model.addAttribute("runelistTime", Vote.getMostRecentCompletedVote(db, account.id(), 106));
        return "vote";
    }

    private final AtomicReference<List<Map<String, Object>>> topVoters = new AtomicReference<>(Collections.emptyList());

    @Scheduled(fixedDelay = 5000)
    public void updateTopVoters() {
        topVoters.set(db.queryForList("SELECT account.displayname, COUNT(player_id) AS monthlyCount\n" +
                "FROM votes\n" +
                "JOIN accounts AS account ON player_id = account.id\n" +
                "WHERE extract (month FROM vote_on) = extract (month FROM CURRENT_DATE)\n" +
                "GROUP BY player_id, account.displayname\n" +
                "ORDER BY COUNT(player_id) DESC\n" +
                "LIMIT 10;"));
    }

}
