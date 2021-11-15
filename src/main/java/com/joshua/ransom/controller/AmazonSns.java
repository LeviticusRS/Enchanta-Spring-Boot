package com.joshua.ransom.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import com.joshua.ransom.model.api.SesMessage;
import com.joshua.ransom.model.api.SnsNotification;
import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Joshua Ransom on 10/17/2020.
 */
@Controller
public class AmazonSns {

	private final JdbcTemplate db;


	public static final String KEY = "AKIAI56T66III74ES6DA";
	public static final String SECRET = "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";
	private static final AWSCredentials credentials = new BasicAWSCredentials(KEY, SECRET);
	private static final ThreadLocal<AmazonSimpleEmailServiceClient> CLIENTLOCAL =
			ThreadLocal.withInitial(() -> new AmazonSimpleEmailServiceClient(credentials));
	private static final Region region = Region.getRegion(Regions.US_WEST_2);
	private static final Executor EXECUTOR = Executors.newFixedThreadPool(64);

	public AmazonSns(JdbcTemplate db) {
		this.db = db;
	}

	/*

	insert into newsletter_emails(email, username)
  (select distinct on (lower(email)) lower(email), displayname from accounts where email is not null and email != 'no@email.com' );

insert into newsletter_mail_queue(job_id, email_id)
  (SELECT 1, id FROM newsletter_emails WHERE unregistered = FALSE AND bounced = FALSE);

	 */

	@RequestMapping("/campaign/{campaign}/process/{count}")
	public @ResponseBody String doMail(@PathVariable("campaign") Integer campaign, @PathVariable("count") Integer count) {
		Map<String, Object> jobDetails = db.queryForMap("SELECT * FROM newsletter_jobs WHERE id = ?", campaign);
		List<Map<String, Object>> tenJobs = db.queryForList("SELECT newsletter_mail_queue.id, username, email, unregister_key FROM newsletter_mail_queue LEFT JOIN newsletter_emails ON email_id = newsletter_emails.id WHERE completed = FALSE ORDER BY unregister_key ASC LIMIT " + count);

		String title = (String) jobDetails.get("subject");
		String html = (String) jobDetails.get("html_body");
		String text = (String) jobDetails.get("text_body");
		for (Map<String, Object> job : tenJobs) {
			Long id = (Long) job.get("id");
			String email = (String) job.get("email");
			String text2 = text.replace("{{user}}", (String) job.get("username")).replace("{{unsub_key}}", (String) job.get("unregister_key")).replace("{{email}}", email).replace("{{id}}", String.valueOf(id));
			String html2 = html.replace("{{user}}", (String) job.get("username")).replace("{{unsub_key}}", (String) job.get("unregister_key")).replace("{{email}}", email).replace("{{id}}", String.valueOf(id));

			db.update("UPDATE newsletter_mail_queue SET completed = TRUE WHERE id = ?", id);
			EXECUTOR.execute(() -> {
				try {
					mail(email, title, html2, text2);
					System.out.println("Sent to " + email + ".");
				} catch (Exception e) {
					db.update("UPDATE newsletter_mail_queue SET completed = FALSE WHERE id = ?", id);
					e.printStackTrace();
				}
			});
		}

		return "Processed! " + db.queryForObject("SELECT count(1) FROM newsletter_mail_queue WHERE completed = FALSE", Integer.class) + " remaining.";
	}

	@RequestMapping(path = "/ses-api", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String sesApi(@RequestBody String body) {
		SnsNotification notification = new Gson().fromJson(body, SnsNotification.class);
		String message = notification.getMessage();
		SesMessage sesMessage = new Gson().fromJson(message, SesMessage.class);

		if (sesMessage.getNotificationType().equals("Delivery")) {
			for (String recipient : sesMessage.getDelivery().getRecipients()) {
				db.update("UPDATE newsletter_emails SET delivered = TRUE WHERE email = ?", recipient);
			}
		} else if (sesMessage.getNotificationType().equals("Bounce")) {
			for (SesMessage.BounceRecipient recipient : sesMessage.getBounce().bouncedRecipients) {
				db.update("UPDATE newsletter_emails SET bounced = TRUE WHERE email = ?", recipient.emailAddress);
			}
		} else {
			System.out.println(message);
			System.out.println();
			System.out.println(message);
		}

		return "OK";
	}

	private static void mail(String to, String title, String html, String text) {
		Destination destination = new Destination().withToAddresses(to);
		AmazonSimpleEmailServiceClient client = CLIENTLOCAL.get();

		// Create the subject and body of the message.
		Content subject = new Content(title);
		Body body = new Body().withHtml(new Content(html)).withText(new Content(text));

		// Create a message with the specified subject and body.
		Message message = new Message().withSubject(subject).withBody(body);

		// Assemble the email.
		SendEmailRequest request = new SendEmailRequest().withSource("OS-Scape <info@os-scape.com>")
				.withDestination(destination).withMessage(message);
		client.setRegion(region);
		client.sendEmail(request);
	}

	@RequestMapping("/email/unsubscribe/{key}/{email:.*}")
	public @ResponseBody String unsubscribe(@PathVariable("key") String key, @PathVariable("email") String email) {
		List<Map<String, Object>> maps = db.queryForList("UPDATE newsletter_emails SET unregistered = TRUE where email = ? AND unregister_key = ? AND newsletter_emails.unregistered = FALSE RETURNING 1", email, key);
		if (maps.size() > 0) {
			return "Unsubscribed!";
		} else {
			return "Could not find that email. Are you already unsubscribed, maybe?";
		}
	}

	@RequestMapping("/track/open/{id}/{email:.*}")
	public @ResponseBody String trackOpen(@PathVariable("id") Integer id, @PathVariable("email") String email) {
		db.update("UPDATE newsletter_emails SET opened = TRUE WHERE email = ?", email);
		db.update("UPDATE newsletter_jobs SET opens = opens + 1 WHERE id = ?", id);
		return "";
	}

}
