package com.joshua.ransom.service;

import com.joshua.ransom.model.Vote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VoteService {

    private final JdbcTemplate db;

    public VoteService(JdbcTemplate jdbcTemplate) {
        db = jdbcTemplate;
    }

    public String generateVoteKey() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public void callback(String uri) {
        log.info("Got Callback uri : " + uri);

        Optional<Vote> voteOptional = Optional.ofNullable(Vote.findByVoteKey(db, uri));

        if(voteOptional.isPresent()) {
            Vote vote = voteOptional.get();
            vote.setVoteOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
            int worked = db.update("UPDATE votes SET vote_on = ? WHERE vote_key = ?", vote.getVoteOn(), vote.getVoteKey());
            if (worked > 0)
                log.info("Completed Vote");
            else
                log.info("Completed Vote didn't update");
        }

        log.info("Exiting viewVote service");

    }
}
