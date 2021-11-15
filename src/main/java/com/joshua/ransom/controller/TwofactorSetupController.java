package com.joshua.ransom.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by Joshua Ransom on 2/4/2020.
 */
@Controller
public class TwofactorSetupController {

	private final JdbcTemplate db;

	public TwofactorSetupController(JdbcTemplate db) {
		this.db = db;
	}


	@RequestMapping(value = "/2fa**")
	public String home(Map<String, Object> model, HttpServletRequest request) {
		List<Map<String, Object>> invite = db.queryForList("SELECT * FROM twofactor_invites WHERE id = ? AND expires_on > now()", request.getParameter("auth"));

		if (!invite.isEmpty()) {
			Map<String, Object> inviteData = invite.get(0);
			String otpUrl = "otpauth://totp/Enchanta:" + inviteData.get("player_name_url") + "?secret=" + inviteData.get("code") + "&issuer=Enchanta";
			model.put("invite_image", "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=" + URLEncoder.encode(otpUrl));
			model.put("invite", inviteData);
		}

		return "twofactor_setup";
	}

}
