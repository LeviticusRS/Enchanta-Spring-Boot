package com.joshua.ransom.controller;

import com.joshua.ransom.model.Account;
import com.joshua.ransom.util.Sessions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class GlobalControllerAdvice {
    private Integer count;
    private final JdbcTemplate db;

    public GlobalControllerAdvice(JdbcTemplate db) {
        this.db = db;
    }

    @ModelAttribute("account")
    public Account getAccount() {
        if (Sessions.has()) {
            return Account.populateView(db);
        }
        return null;
    }

    @ModelAttribute("playerCount")
    public int populatePlayersOnline() {
        return count;
    }

    @Scheduled(fixedDelay = 500)
    public void updateCounts() {
        count = db.queryForObject("SELECT players FROM online_statistics WHERE world = 1", Integer.class);
    }

}