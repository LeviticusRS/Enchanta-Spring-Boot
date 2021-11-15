package com.joshua.ransom.model.api;

/**
 * Created by Joshua Ransom on 10/17/2020.
 */
public class SnsNotification {

	private String Type;
	private String MessageId;
	private String Message;

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		this.Type = type;
	}

	public String getMessage() {
		return Message;
	}

	public String getMessageId() {
		return MessageId;
	}

	public void setMessage(String message) {
		this.Message = message;
	}

	public void setMessageId(String messageId) {
		this.MessageId = messageId;
	}

}
