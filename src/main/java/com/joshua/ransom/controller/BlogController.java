package com.joshua.ransom.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Joshua Ransom on 5/28/2021.
 */
@Controller
@Transactional
public class BlogController {
	private final JdbcTemplate db;

	public BlogController(JdbcTemplate db) {
		this.db = db;
	}

	@RequestMapping(value = "/blog")
	public String home(Map<String, Object> model) {
		return "blog";
	}

}
