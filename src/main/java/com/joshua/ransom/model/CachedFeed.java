package com.joshua.ransom.model;

import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Joshua Ransom on 5/23/2020.
 */
public class CachedFeed {

	private String url;
	private String base;
	private int size = 10;
	private AtomicReference<Entry[]> entries = new AtomicReference<>(new Entry[] {});

	public CachedFeed(String url, String base) {
		this.url = url;
		this.base = base;
	}

	public void update() {
		try {
			Scanner scanner = new Scanner(new URL(url).openStream());
			String lines = "";

			while (scanner.hasNextLine()) {
				lines += scanner.nextLine();
			}

			scanner.close();

			Gson gson = new Gson();
			Entry[] entries = gson.fromJson(lines, Entry[].class);
			for (Entry e : entries) {
				e.base = base;
			}

			// Resize array
			Entry[] e_s = new Entry[size];
			System.arraycopy(entries, 0, e_s, 0, Math.min(size, entries.length));

			// Lazily put the new value to avoid conflicting with concurrent reading
			this.entries.lazySet(e_s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Entry[] entries() {
		return entries.get();
	}

	public static class Entry {

		private String base;
		public int threadid;
		public String title;
		public long dateline;
		public String postusername;
		public String title_seo;

		public String getTitle() {
			return title;
		}

		public String getStartername() {
			return postusername;
		}

		public String getUrl() {
			return base + "/showthread.php?" + threadid;
		}

		public Timestamp getDate() {
			return new Timestamp(dateline * 1000);
		}

		/*
		tid: "6637",
title: "Staking Commentary what are those last hits at the end ?",
state: "open",
posts: "1",
starter_id: "4049",
start_date: "1464007305",
last_poster_id: "4049",
last_post: "1464007305",
starter_name: "Shark",
last_poster_name: "Shark",
poll_state: null,
last_vote: null,
views: "2",
forum_id: "27",
approved: "1",
author_mode: null,
pinned: "0",
moved_to: null,
topic_firstpost: "38124",
topic_queuedposts: "0",
topic_open_time: "0",
topic_close_time: "0",
topic_rating_total: "0",
topic_rating_hits: "0",
title_seo: "staking-commentary-what-are-those-last-hits-at-the-end",
moved_on: "0",
topic_archive_status: "0",
last_real_post: "1464007305",
topic_answered_pid: "0",
popular_time: null,
featured: "0",
question_rating: null,
topic_hiddenposts: "0"
		 */
	}

}
