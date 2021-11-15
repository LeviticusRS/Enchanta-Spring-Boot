package com.joshua.ransom.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Vote {

	public static VoteMapper VOTE_MAPPER = new VoteMapper();

	private int id;
	private int userId;
	private String ipAddress;
	private String voteKey;
	private Timestamp startedOn;
	private Timestamp voteOn;
	private int claimed = 1;
	private int siteId;

	public Vote() {
	}

	public int id() {
		return id;
	}

	static class VoteMapper implements RowMapper<Vote> {

		@Override
		public Vote mapRow(ResultSet rs, int rowNum) throws SQLException {
			Vote vote = new Vote();
			vote.id = rs.getInt("id");
			vote.userId = rs.getInt("player_id");
			vote.ipAddress = rs.getString("ip_address");
			vote.voteKey = rs.getString("vote_key");
			vote.startedOn = rs.getTimestamp("started_on");
			vote.voteOn = rs.getTimestamp("vote_on");
			vote.claimed = rs.getInt("claimed");
			vote.siteId = rs.getInt("site_id");
			return vote;
		}

	}


	public static Vote findByVoteKey(JdbcTemplate jdbcTemplate, String key) {
		try {
			String sql = "SELECT * FROM votes WHERE vote_key = '" + key + "'";
			return jdbcTemplate.queryForObject(sql, VOTE_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public static Vote getMostRecentVote(JdbcTemplate jdbcTemplate, int playerId, String ipAddress, int siteId) {
		try {
			String sql = "SELECT * FROM votes WHERE site_id="+siteId+" AND (player_id="+playerId+" OR ip_address='"+ipAddress+"') AND started_on > now() - INTERVAL '12' HOUR AND vote_on IS NULL ORDER BY started_on DESC LIMIT 1";
			return jdbcTemplate.queryForObject(sql, VOTE_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public static String getMostRecentCompletedVote(JdbcTemplate jdbcTemplate, int playerId, int siteId) {
		try {
			String sql = "SELECT * FROM votes WHERE site_id="+siteId+" AND player_id="+playerId+" AND vote_on IS NOT NULL AND vote_on > now() - INTERVAL '12' HOUR ORDER BY vote_on DESC LIMIT 1";
			long successfulVote = jdbcTemplate.queryForObject(sql, VOTE_MAPPER).getVoteOn().getTime() + 43200000L;
			long diff = successfulVote - System.currentTimeMillis();
			return msToTime(diff);
		} catch (EmptyResultDataAccessException | NullPointerException e) {
			return null;
		}
	}

	public static String msToTime(long millis) {
		return String.format("%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis) -
						TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis) -
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	}

}
