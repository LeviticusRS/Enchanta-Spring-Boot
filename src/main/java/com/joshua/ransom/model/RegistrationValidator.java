package com.joshua.ransom.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Joshua Ransom on 6/26/2020.
 */
@Component
public class RegistrationValidator {

	private static final Pattern USERNAME_MATCH = Pattern.compile("^[a-zA-Z0-9- ]+$");
	private static final Pattern USERNAME_DISALLOWED = Pattern.compile("^[- ]{2,}");
	private static final Pattern USERNAME_DISALLOWED_2 = Pattern.compile("(^([- ]))|(([- ])$)");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");

	private final JdbcTemplate db;

	public RegistrationValidator(JdbcTemplate db) {
		this.db = db;
	}

	public Set<RegistrationError> validate(RegistrationForm form) {
		// Fix the username.
		boolean uppercaseFirst = true;
		for (char c : form.getUsername().toCharArray()) {
			if (Character.isUpperCase(c)) {
				uppercaseFirst = false;
			}
		}

		// Full-lowercase names aren't cool
		if (uppercaseFirst) {
			form.setUsername(Character.toUpperCase(form.getUsername().charAt(0)) + form.getUsername().substring(1));
		}

		// Trim data
		form.setUsername(form.getUsername().trim());
		form.setPassword(form.getPassword().trim());
		form.setEmail(form.getEmail().trim());

		Set<RegistrationError> errors = new HashSet<>();

		// Validate passwords
		if (form.getPassword().equals(form.getRepeatpassword())) {
			errors.add(RegistrationError.PASSWORD_MISMATCH);
		}

		// Validate password legitimacy
		if (form.getPassword().length() < 6) {
			errors.add(RegistrationError.MIN_LENGTH);
		}

		// Validate password legitimacy
		if (form.getPassword().length() > 15) {
			errors.add(RegistrationError.MAX_LENGTH);
		}

		if (USERNAME_DISALLOWED.matcher(form.getPassword()).matches() || USERNAME_DISALLOWED_2.matcher(form.getPassword()).matches()) {
			errors.add(RegistrationError.PASSWORD_INVALID);
		}

		// Validate username
		String user = form.getUsername();

		if (user.length() < 3 || user.length() > 12) {
			errors.add(RegistrationError.INVALID_USERNAME);
		}

		if (USERNAME_DISALLOWED.matcher(user).matches() || USERNAME_DISALLOWED_2.matcher(user).matches()) {
			errors.add(RegistrationError.INVALID_USERNAME);
		}

		// Validate email
		if (!EMAIL_PATTERN.matcher(form.getEmail()).matches()) {
			errors.add(RegistrationError.INVALID_EMAIL);
		}


		List<Map<String, Object>> maps = db.queryForList("SELECT username, displayname, email FROM accounts WHERE username = ? OR LOWER(displayname) = ? OR LOWER(email) = ?", form.getUsername(), form.getUsername().toLowerCase(), form.getEmail().toLowerCase());
		if (!maps.isEmpty()) {
			Map<String, Object> data = maps.get(0);
			if (data.get("displayname").toString().equalsIgnoreCase(form.getUsername()) || data.get("username").toString().equalsIgnoreCase(form.getUsername())) {
				errors.add(RegistrationError.USERNAME_TAKEN);
			}
		}
		return errors;
	}
}
