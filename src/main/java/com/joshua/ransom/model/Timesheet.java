package com.joshua.ransom.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Joshua Ransom on 3/1/2020.
 */
public class Timesheet {

	private int id;
	private int accountId;
	private Timestamp timeIn;
	private Timestamp timeOut;

	public int id() {
		return id;
	}

	public int account() {
		return accountId;
	}

	public Timestamp timeIn() {
		return timeIn;
	}

	public Timestamp timeOut() {
		return timeOut;
	}

	public String difference() {
		long millis = (timeOut == null ? System.currentTimeMillis() : timeOut.getTime()) - timeIn().getTime();
		return String.format("%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis) -
						TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis) -
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	}

	public static final class Mapper implements RowMapper<Timesheet> {

		@Override public Timesheet mapRow(ResultSet rs, int rowNum) throws SQLException {
			Timesheet product = new Timesheet();
			product.id = rs.getInt("id");
			product.accountId = rs.getInt("account_id");
			product.timeIn = rs.getTimestamp("time_in");
			product.timeOut = rs.getTimestamp("time_out");
			return product;
		}
	}

	public static Mapper MAPPER = new Mapper();

	public static List<Timesheet> findByAccountId(JdbcTemplate jdbcTemplate, int accountId) {
		String sql = "SELECT * FROM staff_timesheet WHERE account_id = " + accountId;
		return jdbcTemplate.query(sql, MAPPER);
	}

	public static List<Timesheet> findByLastClockIn(JdbcTemplate jdbcTemplate, int accountId) {
		String sql = "SELECT * FROM staff_timesheet WHERE account_id = " + accountId + " AND time_out IS NULL;";
		return jdbcTemplate.query(sql, MAPPER);
	}

	public static void setLastTimeSheetClockedOut(JdbcTemplate jdbcTemplate, int accountId) {
		String sql = "UPDATE staff_timesheet SET time_out = now() WHERE account_id = " + accountId + " AND time_out IS NULL;";
		jdbcTemplate.update(sql);
	}

	public static void insertTimeSheetClockedIn(JdbcTemplate jdbcTemplate, int accountId) {
		String sql = "INSERT into staff_timesheet(account_id) VALUES (" + accountId + ");";
		jdbcTemplate.update(sql);
	}

	public static List<Timesheet> findAll(JdbcTemplate jdbcTemplate, int accountId) {
		String sql = "SELECT * FROM staff_timesheet WHERE account_id = " + accountId + " ORDER BY time_out DESC;";
		return jdbcTemplate.query(sql, MAPPER);
	}

}
