package com.joshua.ransom;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.serverside.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class Website {
	private static WebhookClient donationDiscordClient;
	private static WebhookClient cartDiscordClient;
	public static final String ACCESS_TOKEN = "EAAGZB72Kv7jsBAN4bfxy9j8cpkUZCe8PVqY7vpDZChJtaoZBZBZC7iBWxoyDtCm5ZA6vX97" +
			"gibXthRcNz8lC3jxza9Yz5I42ubb9QU9CErdrJjtXSRTnPjUEd7q43vD0l9ZCPP3YxjjZCYvW6kXyo31SkZAjKKPgV8VNMxIXzDpoAmyfv" +
			"JpTvL5PAV27ohyv7h9WkZD";
	public static final String PIXEL_ID = "130145069107708";

	private static final APIContext context = new APIContext(ACCESS_TOKEN);

	public static void main(String[] args) {
		SpringApplication.run(Website.class, args);
		// Using the builder
		WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/84038055318991" +
				"6694/VKV4rrJD4Iqylls0Xd8tm8YVepeBt_gg-q9ma_vPNO93O7--DRtrtOQBs-RqAQSGVMWW"); // or id, token
		builder.setThreadFactory((job) -> {
			Thread thread = new Thread(job);
			thread.setName("Discord");
			thread.setDaemon(true);
			return thread;
		});
		builder.setWait(true);
		donationDiscordClient = builder.build();
		// Using the builder
		builder = new WebhookClientBuilder("https://discord.com/api/webhooks/844703141073256468/5JkDAhfsBvNvdwgh" +
				"27W7FmF94_xLRfzggrgsO66Ycwjlz7ilGuiio7CpW1o-vP9UNOb1"); // or id, token
		builder.setThreadFactory((job) -> {
			Thread thread = new Thread(job);
			thread.setName("Discord");
			thread.setDaemon(true);
			return thread;
		});
		builder.setWait(true);
		cartDiscordClient = builder.build();

		getContext().setLogger(System.out);
	}

	public static WebhookClient donationDiscord() {
		return donationDiscordClient;
	}

	public static WebhookClient cartDiscord() {
		return cartDiscordClient;
	}

	public static APIContext getContext() {
		return context;
	}

}
