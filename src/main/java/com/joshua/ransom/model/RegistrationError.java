package com.joshua.ransom.model;

/**
 * Created by Joshua Ransom on 6/26/2020.
 */
public enum RegistrationError {

	NONE(""),
	USERNAME_TAKEN("Username is already in use."),
	EMAIL_TAKEN("Email is already in use."),
	INVALID_EMAIL("Invalid email address."),
	INVALID_USERNAME("Invalid username."),
	PASSWORD_MISMATCH("Passwords do not match."),
	PASSWORD_INVALID("Invalid or too weak password."),
	MIN_LENGTH("Min password length is 6."),
	MAX_LENGTH("Max password length is 20."),
	TERMS_NOT_ACCEPTED("Terms have not been accepted."),
	PASSWORD_INVALID_FOR_PAST_PLAYER("Password does not match your past password");

	private String message;

	RegistrationError(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
