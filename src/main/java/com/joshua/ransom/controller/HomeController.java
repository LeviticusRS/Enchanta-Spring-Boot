package com.joshua.ransom.controller;

import com.google.gson.Gson;
import com.joshua.ransom.model.CachedFeed;
import com.joshua.ransom.util.Sessions;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Joshua Ransom on 2/4/2020.
 */
@Controller

@Transactional
public class HomeController {

	private final JdbcTemplate db;

	private final Environment env;

	private static final Gson GSON = new Gson();

	private ForkJoinPool executor = new ForkJoinPool(2);
	private AtomicReference<List<Map<String, Object>>> homeNews = new AtomicReference<>(Collections.emptyList());

	public HomeController(Environment env, JdbcTemplate db) {
		this.env = env;
		this.db = db;
	}

	@RequestMapping(value = "/")
	public String home(Map<String, Object> model) {
		model.put("news", homeNews.get());
		return "main";
	}

	@RequestMapping(value = "/tos")
	public String termsOfService() {
		return "tos";
	}

	@RequestMapping(value = "/deals")
	public String donationDeals() {
		return "deals";
	}

	@RequestMapping(value = "/discord")
	public String discord() {
		return "redirect:https://discord.gg/4auGNkCTSU";
	}

	@Scheduled(fixedDelay = 5000)
	public void updateNews() {
		homeNews.set(db.queryForList("SELECT * FROM homepage_news ORDER BY time DESC LIMIT 4"));
	}

}
