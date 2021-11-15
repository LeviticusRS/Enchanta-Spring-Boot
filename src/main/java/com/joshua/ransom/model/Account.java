package com.joshua.ransom.model;


import com.joshua.ransom.util.Sessions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Joshua Ransom on 3/3/2020.
 */
public class Account {

	public static AccountMapper ACCOUNT_MAPPER = new AccountMapper();

	private int id;
	private String username;
	private String password;
	private String displayName;
	private String email;
	private int credits;
	private int rights;

	private Account() {
		// Private to avoid constructing publicly
	}

	public int id() {
		return id;
	}

	public String displayName() {
		return displayName;
	}

	public int credits() {
		return credits;
	}

	public String getEmail() {
		return email;
	}

	public int getRights() {
		return rights;
	}

	static class AccountMapper implements RowMapper<Account> {

		@Override
		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
			Account account = new Account();
			account.id = rs.getInt("id");
			account.password = rs.getString("password");
			account.username = rs.getString("username");
			account.displayName = rs.getString("displayname");
			account.email = rs.getString("email");
			account.credits = rs.getInt("credits");
			account.rights = rs.getInt("rights");
			return account;
		}

	}


	public static Account findByAccountId(JdbcTemplate jdbcTemplate, int id) {
		String sql = "SELECT * FROM accounts WHERE id = " + id;
		return jdbcTemplate.queryForObject(sql, ACCOUNT_MAPPER);
	}

	public static List<Account> findByAccountUsername(JdbcTemplate jdbcTemplate, String username) {
		String sql = "SELECT DISTINCT * FROM accounts WHERE UPPER(displayname) = UPPER('"+username+"')";
		return jdbcTemplate.query(sql, ACCOUNT_MAPPER);
	}

	public static Account populateView(JdbcTemplate jdbcTemplate) {
		int accountId = Integer.parseInt(Sessions.current().getAttribute("account").toString());
		return findByAccountId(jdbcTemplate, accountId);
	}

}
