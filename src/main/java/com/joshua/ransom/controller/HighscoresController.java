/*
package com.bitgate.osscape.store.controller;

import CachedFeed;
import Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

*/
/**
 * Created by Joshua Ransom on 2/4/2020.
 *//*

@Controller
@Transactional
public class HighscoresController {

	private static final String[] SKILL_NAMES = {
			"Attack", "Defence", "Strength", "Hitpoints", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching",
			"Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer",
			"Farming", "Runecrafting", "Hunter", "Construction", "??", "??"
	};

	private final JdbcTemplate db;

	private final Environment env;

	public HighscoresController(JdbcTemplate db, Environment env) {
		this.db = db;
		this.env = env;
	}

	@RequestMapping(value = "/highscores")
	public String index(Map<String, Object> model, HttpServletRequest request) {
		return "redirect:/highscores/3/2/overall";
	}

	@RequestMapping(value = "/highscores/{server}/personal")
	public String personalHighscores(Map<String, Object> model, HttpServletRequest request, @PathVariable("server") Integer server) {
		String user = request.getParameter("user");
		return personalHighscores0(model, request, server, user);
	}

	@RequestMapping(value = "/highscores/{server}/personal/{user}")
	public String personalHighscores0(Map<String, Object> model, HttpServletRequest request, @PathVariable("server") Integer server, @PathVariable("user") String user) {
		List<Map<String, Object>> users = db.queryForList("SELECT * FROM accounts WHERE LOWER(displayname) = ?", user.toLowerCase());
		if (users.isEmpty()) {
			model.put("account_found", false);
			model.put("character_found", false);
			model.put("overall", new HashMap<String, Object>() {
				{
					put("game_mode", 2);
					put("iron_mode", 0);
				}
			});
			model.put("display_name", user);
			return "highscores-personal";
		}

		Map<String, Object> acc = users.get(0);
		model.put("account_found", true);
		model.put("display_name", acc.get("displayname"));
		int accid = ((Integer)acc.get("id"));
		model.put("server", server);
		model.put("user", acc);

		try {
			if (server == 2) {
				List<Map<String, Object>> skills = new LinkedList<>();

				skills.add(db.queryForMap("SELECT rank, kills as value, 'Kills' as statistic, 'attack' as icon FROM pvp_highscores_kills WHERE account_id = ?", accid));
				skills.add(db.queryForMap("SELECT rank, deaths as value, 'Deaths' as statistic, 'defence' as icon FROM pvp_highscores_deaths WHERE account_id = ?", accid));
				skills.add(db.queryForMap("SELECT rank, killstreak as value, 'Killstreak' as statistic, 'strength' as icon FROM pvp_highscores_killstreaks WHERE account_id = ?", accid));
				skills.add(db.queryForMap("SELECT rank, shutdown as value, 'Shutdown' as statistic, 'hitpoints' as icon FROM pvp_highscores_shutdowns WHERE account_id = ?", accid));

				model.put("overall", new HashMap<String, Object>() {
					{
						put("game_mode", 2);
						put("iron_mode", 0);
					}
				});
				model.put("skills", skills);
				model.put("character_found", true);

				return "highscores-personal-pvp";
			}

			Map<String, Object> overall = db.queryForMap("SELECT eco_overall_table.*, displayname " +
					"FROM eco_overall_table LEFT JOIN accounts ON accounts.id = account_id\n" +
					"WHERE account_id = ? AND service_id = ?", accid, server);
			List<Map<String, Object>> skills = db.queryForList("SELECT eco_skill_table.*, displayname " +
					"FROM eco_skill_table LEFT JOIN accounts ON accounts.id = account_id\n" +
					"WHERE account_id = ? AND service_id = ? AND skill != 23 " +
					"ORDER BY skill ASC", accid, server);

			// Fix overall gamemode to 10 if iron man
			if (overall.get("iron_mode").equals(1)) {
				overall.put("game_mode", 10);
			}

			// Post-process for names
			for (Map<String, Object> h : skills) {
				h.put("name", SKILL_NAMES[(Integer) h.get("skill")]);
			}

			model.put("skills", skills);
			model.put("overall", overall);
			model.put("character_found", true);

			return "highscores-personal";
		} catch (EmptyResultDataAccessException erdae) {
			model.put("character_found", false);
			model.put("overall", new HashMap<String, Object>() {
				{
					put("game_mode", 2);
					put("iron_mode", 0);
				}
			});
			return "highscores-personal";
		}
	}

	@RequestMapping(value = "/highscores/{server}/{gamemode}/{skill}")
	public String skillHighscores(Map<String, Object> model, HttpServletRequest request, @PathVariable("server") Integer server,
	                              @PathVariable("skill") String skill, @PathVariable("gamemode") Integer gamemode) {
		return skillHighscores(model, request, server, skill, gamemode, 1);
	}

	@RequestMapping(value = "/highscores/{server}/{gamemode}/{skill}/{page}")
	public String skillHighscores(Map<String, Object> model, HttpServletRequest request, @PathVariable("server") Integer server,
	                              @PathVariable("skill") String skill, @PathVariable("gamemode") Integer gamemode, @PathVariable("page") Integer page) {
		int mode = modeToId(skill);

		if (server == 3 && gamemode != 10) {
			gamemode = 2;
		}

		if (server == 2 && !skill.equalsIgnoreCase("kills") && !skill.equalsIgnoreCase("deaths") &&
				!skill.equalsIgnoreCase("shutdown") && !skill.equalsIgnoreCase("killstreak")) {
			return "redirect:/highscores/2/2/kills";
		}

		model.put("server", server);
		model.put("gamemode", gamemode);
		model.put("skillname", mode == -1 ? "Overall" : SKILL_NAMES[mode]);
		model.put("skill", skill.toLowerCase());

		int ironmode = 0;
		if (gamemode == 10) {
			ironmode = 1;
			gamemode = 2;
		}

		int count = 0;
		int offset = 0;
		int limit = 30;

		Paginator paginator = null;

		List<Map<String, Object>> hs = null;
		if (server == 2) {
			if (skill.equalsIgnoreCase("deaths")) {
				count = db.queryForObject("SELECT count(1) FROM pvp_highscores_deaths", Integer.class);
				paginator = new Paginator(page, 30, count);
				model.put("skillname", "Deaths");

				hs = db.queryForList("SELECT pvp_highscores_deaths.*, displayname FROM pvp_highscores_deaths LEFT JOIN accounts ON accounts.id = account_id ORDER BY rank ASC OFFSET ? LIMIT ?", paginator.getSkip(), limit);
			} else if (skill.equalsIgnoreCase("kills")) {
				count = db.queryForObject("SELECT count(1) FROM pvp_highscores_kills", Integer.class);
				paginator = new Paginator(page, 30, count);
				model.put("skillname", "Kills");

				hs = db.queryForList("SELECT pvp_highscores_kills.*, displayname FROM pvp_highscores_kills LEFT JOIN accounts ON accounts.id = account_id ORDER BY rank ASC OFFSET ? LIMIT ?", paginator.getSkip(), limit);
			} else if (skill.equalsIgnoreCase("killstreak")) {
				count = db.queryForObject("SELECT count(1) FROM pvp_highscores_killstreaks", Integer.class);
				paginator = new Paginator(page, 30, count);
				model.put("skillname", "Killstreak");

				hs = db.queryForList("SELECT pvp_highscores_killstreaks.*, displayname FROM pvp_highscores_killstreaks LEFT JOIN accounts ON accounts.id = account_id ORDER BY rank ASC OFFSET ? LIMIT ?", paginator.getSkip(), limit);

				// Post-process
				for (Map<String, Object> entry : hs) {
					int ks = (Integer) entry.get("killstreak");
					entry.put("bounty", 10 * ks + 50 * (ks / 10));
				}
			} else if (skill.equalsIgnoreCase("shutdown")) {
				count = db.queryForObject("SELECT count(1) FROM pvp_highscores_shutdowns", Integer.class);
				paginator = new Paginator(page, 30, count);
				model.put("skillname", "Shutdown");

				hs = db.queryForList("SELECT pvp_highscores_shutdowns.*, displayname FROM pvp_highscores_shutdowns LEFT JOIN accounts ON accounts.id = account_id ORDER BY rank ASC OFFSET ? LIMIT ?", paginator.getSkip(), limit);
			}
		} else {
			if (mode == -1) {
				count = db.queryForObject("SELECT count(1) FROM eco_overall_table " + "WHERE iron_mode = ? AND game_mode = ? AND service_id = ?", Integer.class, ironmode, gamemode, server);
				paginator = new Paginator(page, 30, count);

				hs = db.queryForList("SELECT eco_overall_table.*, total_level AS lvl, total_xp AS xp, displayname " + "FROM eco_overall_table LEFT JOIN accounts ON accounts.id = account_id\n" + "WHERE iron_mode = ? AND game_mode = ? AND service_id = ?\n" + "ORDER BY rank ASC OFFSET ? LIMIT ?", ironmode, gamemode, server, paginator.getSkip(), limit);
			} else {
				count = db.queryForObject("SELECT count(1) FROM eco_skill_table " + "WHERE iron_mode = ? AND game_mode = ? AND service_id = ?", Integer.class, ironmode, gamemode, server);
				paginator = new Paginator(page, 30, count);

				hs = db.queryForList("SELECT eco_skill_table.*, displayname " + "FROM eco_skill_table LEFT JOIN accounts ON accounts.id = account_id\n" + "WHERE iron_mode = ? AND game_mode = ? AND skill = ? AND service_id = ?" + "ORDER BY rank ASC OFFSET ? LIMIT ?", ironmode, gamemode, mode, server, paginator.getSkip(), limit);
			}
		}

		model.put("hs", hs);
		model.put("paginator", paginator);

		if (server == 2) {
			return "highscores-pvp";
		} else {
			return "highscores";
		}
	}

	private static int modeToId(String mode) {
		switch (mode.toLowerCase().trim()) {
			case "overall": return -1;
			case "attack": return 0;
			case "defence": return 1;
			case "strength": return 2;
			case "hitpoints": return 3;
			case "ranged": return 4;
			case "prayer": return 5;
			case "magic": return 6;
			case "cooking": return 7;
			case "woodcutting": return 8;
			case "fletching": return 9;
			case "fishing": return 10;
			case "firemaking": return 11;
			case "crafting": return 12;
			case "smithing": return 13;
			case "mining": return 14;
			case "herblore": return 15;
			case "agility": return 16;
			case "thieving": return 17;
			case "slayer": return 18;
			case "farming": return 19;
			case "runecrafting": return 20;
			case "hunter": return 21;
			case "construction": return 22;
		}

		return -1;
	}

}
*/
