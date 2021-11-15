package com.joshua.ransom.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * Created by Joshua Ransom on 7/12/2020.
 */
public class RecaptchaResponse {

	private boolean success;
	private String challenge_ts;
	private String hostname;

	@JsonProperty("error-codes")
	private String[] errorCodes;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String[] getErrorCodes() {
		return errorCodes;
	}

	public String getChallenge_ts() {
		return challenge_ts;
	}

	public void setChallenge_ts(String challenge_ts) {
		this.challenge_ts = challenge_ts;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setErrorCodes(String[] errorCodes) {
		this.errorCodes = errorCodes;
	}

	@Override
	public String toString() {
		return "Response[success=" + success + ", errors=" + Arrays.toString(errorCodes) + "]";
	}
}
