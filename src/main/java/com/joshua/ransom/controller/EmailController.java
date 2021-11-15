package com.joshua.ransom.controller;

import com.joshua.ransom.model.Account;
import com.joshua.ransom.util.Sessions;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.primeframework.transformer.domain.Document;
import org.primeframework.transformer.service.BBCodeParser;
import org.primeframework.transformer.service.BBCodeToHTMLTransformer;
import org.primeframework.transformer.service.Transformer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class EmailController {


	private final JdbcTemplate jdbcTemplate;

	private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9-._]+@[a-zA-Z0-9.-]+");

	public EmailController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public String email() {
		// If we're not logged in, take us to the login form.
		if (!Sessions.has())
			return "redirect:/login";

		return "email";
	}

	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public String postEmail(HttpServletRequest request, Map<String, Object> model) {
		// Validate email
		String email = request.getParameter("email");
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			return  "redirect:/email";
		}

		int accountId = Integer.parseInt(Sessions.current().getAttribute("account").toString());
		int update = jdbcTemplate.update("UPDATE accounts SET email = ? WHERE id = ?", request.getParameter("email"), accountId);

		Map<String, Object> account = jdbcTemplate.queryForMap("SELECT * FROM accounts WHERE id = ?;", accountId);

		// Store the points in the model map
		model.put("account", account);

		return "redirect:/";
	}

	@GetMapping("/email/verify")
	public String emailVerification(@RequestParam int id, Model model) {
		int update = jdbcTemplate.update("UPDATE accounts SET emailVerified = ? WHERE id = ?",true, id);
		if (update > 0) {
			model.addAttribute("response", "success");
		} else {
			model.addAttribute("response", "failure");
		}
		return "verify";
	}

}