package com.joshua.ransom.model.api;

/**
 * Created by Joshua Ransom on 10/17/2020.
 */
public class SesMessage {

	private String notificationType;
	private Delivery delivery;
	private Bounce bounce;

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setMail(Delivery mail) {
		this.delivery = mail;
	}

	public Bounce getBounce() {
		return bounce;
	}

	public static class Email {
		public Delivery delivery;
	}

	public static class Delivery {
		public String[] recipients;

		public String[] getRecipients() {
			return recipients;
		}

		public void setRecipients(String[] recipients) {
			this.recipients = recipients;
		}
	}

	public static class Bounce {
		public String bounceType;
		public String bounceSubType;
		public BounceRecipient[] bouncedRecipients;
	}

	public static class BounceRecipient {
		public String emailAddress;

		@Override
		public String toString() {
			return emailAddress;
		}
	}

}
