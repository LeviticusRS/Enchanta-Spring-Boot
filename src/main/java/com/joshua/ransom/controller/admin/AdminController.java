package com.joshua.ransom.controller.admin;

import com.joshua.ransom.model.Account;
import com.joshua.ransom.model.Timesheet;
import com.joshua.ransom.util.Sessions;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.primeframework.transformer.domain.Document;
import org.primeframework.transformer.service.BBCodeParser;
import org.primeframework.transformer.service.BBCodeToHTMLTransformer;
import org.primeframework.transformer.service.Transformer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Joshua Ransom on 5/23/2021.
 */
@Controller
public class AdminController {

	private final JdbcTemplate db;

	public AdminController(Environment env, JdbcTemplate db) {
		this.db = db;
	}

	@RequestMapping(value = "/admin")
	public String adminHome(Map<String, Object> model) {
		if (!Sessions.has()) {
			return "error";
		}

        Account account = Account.populateView(db);
        if (account.getRights() < 2 && account.getRights() > 4) {
            return "error";
        }
		List<Timesheet> allSheets = Timesheet.findAll(db, account.id());
		List<Timesheet> lastSheets = Timesheet.findByLastClockIn(db, account.id());
		for (Timesheet sheet : lastSheets) {
			long millis = System.currentTimeMillis() - sheet.timeIn().getTime();
			model.put("clock", String.format("%02d:%02d:%02d",
					TimeUnit.MILLISECONDS.toHours(millis),
					TimeUnit.MILLISECONDS.toMinutes(millis) -
							TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
					TimeUnit.MILLISECONDS.toSeconds(millis) -
							TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
		}
		model.put("allSheets", allSheets);
		return "admin/main";
	}

	@PostMapping("/admin/clock")
	public String clock(@RequestParam int clockType, Model model) {
		Account account = Account.populateView(db);
		if (account == null) {
			return "error";
		}

		if (clockType == 0) {
			Timesheet.setLastTimeSheetClockedOut(db, account.id());
		} else if (clockType == 1) {
			Timesheet.insertTimeSheetClockedIn(db, account.id());
			model.addAttribute("clock", "0:0:0");
		}
		List<Timesheet> allSheets = Timesheet.findAll(db, account.id());
		model.addAttribute("allSheets", allSheets);
		return "admin/main";
	}

	@PostMapping("/admin/submit_blog")
	public String blogSubmit(@RequestParam String input, Model model) {
		System.out.println(input);
		TextProcessor processor = BBProcessorFactory.getInstance().create();
		Account account = Account.populateView(db);
		if (account == null) {
			return "error";
		}

		if (account.getRights() != 3 && account.getRights() != 4) {
			return "error";
		}
		Document document = new BBCodeParser().buildDocument(input, null);
		String html = new BBCodeToHTMLTransformer().transform(document, (node) -> true,
				new Transformer.TransformFunction.HTMLTransformFunction(), null);
		model.addAttribute("response", html);
		return "blog";
	}

	@PostMapping("/admin/custom_donation")
	public String customDonation(@RequestParam String username, @RequestParam String type, @RequestParam String amount,
								 Model model) {
		Account account = Account.populateView(db);
		if (account == null) {
			model.addAttribute("response", "You're not logged in!");
			return "admin/main";
		}

		List<Timesheet> allSheets = Timesheet.findAll(db, account.id());
		model.addAttribute("allSheets", allSheets);

		List<Account> donatorAccount = Account.findByAccountUsername(db, username);
		if (donatorAccount.size() == 0) {
			model.addAttribute("response", "Account doesn't exist");
			return "admin/main";
		}
		int theAmount;
		try {
			theAmount = Integer.parseInt(amount);
		} catch(NumberFormatException exception) {
			model.addAttribute("response", "You're not logged in!");
			return "admin/main";
		}
		String sql = "INSERT into custom_donation(account_id, donator_account_id, amount, type) VALUES (?, ?, ?, ?);";
		db.update(sql, account.id(), donatorAccount.get(0).id(), theAmount, type);
		model.addAttribute("response", "Successfully inserted donation");
		return "admin/main";
	}

}
