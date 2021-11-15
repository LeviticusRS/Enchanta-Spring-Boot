package com.joshua.ransom.model;

import org.springframework.stereotype.Component;

/**
 * Created by Joshua Ransom on 6/25/2020.
 */
@Component
public class RegistrationForm {

	private String username;
	private String email;
	private String password;
	private String repeatpassword;
	private String recaptchaToken;
	private String termsAndRules;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatpassword() {
		return repeatpassword;
	}

	public void setRepeatpassword(String repeatpassword) {
		this.repeatpassword = repeatpassword;
	}

	public String getRecaptchaToken() {
		return recaptchaToken;
	}

	public void setRecaptchaToken(String recaptchaToken) {
		this.recaptchaToken = recaptchaToken;
	}

	public String getTermsAndRules() {
		return termsAndRules;
	}

	public void setTermsAndRules(String termsAndRules) {
		this.termsAndRules = termsAndRules;
	}

}
