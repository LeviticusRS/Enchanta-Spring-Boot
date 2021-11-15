package com.joshua.ransom.controller;

import com.joshua.ransom.util.Sessions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Joshua Ransom on 2/2/2020.
 */
@Controller
public class LoginController {

	private final JdbcTemplate jdbcTemplate;

	public LoginController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(params = "login", value = "/login", method = RequestMethod.POST)
	public String postlogin(HttpServletRequest request, Map<String, Object> model) {
		// Extract parameters from post request..
		String username = request.getParameter("username").replaceAll("%", "").replaceAll("_", " ");
		String password = request.getParameter("password");

		// Resolve both the password and the id:
		List<Map<String, Object>> stored = jdbcTemplate.queryForList("SELECT id, password FROM accounts WHERE displayname ILIKE ?;", username);
		
		// Does this account even exist?
		if (stored.isEmpty()) {
			model.put("error", "Invalid username or password.");
			return "login";
		}

		// Match the password against the stored one.
		String encrypted = (String) stored.get(0).get("password");
		if (password.equals(encrypted)) {
			// If that worked, store the user id in the session.
			Sessions.current().setAttribute("account", stored.get(0).get("id"));
			return "redirect:/";
		} else {
			model.put("error", "Invalid username or password.");
		}

		return "login";
	}

	@RequestMapping(value = "/logout")
	public String logout() {
		Sessions.end();
		return "redirect:/";
	}

}
