package com.joshua.ransom.controller;

import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.serverside.*;
import com.joshua.ransom.model.RegistrationError;
import com.joshua.ransom.model.RegistrationForm;
import com.joshua.ransom.model.RegistrationValidator;
import com.joshua.ransom.model.api.RecaptchaResponse;
import com.joshua.ransom.service.MixPanelService;
import com.joshua.ransom.util.Sessions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static com.joshua.ransom.Website.PIXEL_ID;
import static com.joshua.ransom.Website.getContext;

/**
 * Created by Joshua Ransom on 2/4/2020.
 */
@Controller
public class RegistrationController {

	private final JdbcTemplate db;

	private final RegistrationValidator registrationValidator;

	private final MixPanelService mixPanelService;

	private final ExecutorService executor = new ForkJoinPool();

	public RegistrationController(JdbcTemplate db, RegistrationValidator registrationValidator, MixPanelService mixPanelService) {
		this.db = db;
		this.registrationValidator = registrationValidator;
		this.mixPanelService = mixPanelService;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerForm(Map<String, Object> model, HttpServletRequest request) {
		if(Sessions.has()) {
			return "redirect:/";
		}
		mixPanelService.event("View Registration", Sessions.getClientIp(request));
		model.putAll(request.getParameterMap());
		return "register";
	}
		
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public DeferredResult<String> doRegister(@ModelAttribute("form") RegistrationForm form, HttpServletRequest request, BindingResult result, @RequestParam(value="g-recaptcha-response") String recaptcha, RedirectAttributes redir) {
		form.setRecaptchaToken(recaptcha);
		String ip = Sessions.getClientIp(request);

		DeferredResult<String> t = new DeferredResult<>(TimeUnit.MINUTES.toMillis(5));

		executor.submit(() -> {
			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, String> x = new LinkedMultiValueMap<>();
			x.add("secret", "6LfczMIZAAAAAGAgcHzHgF3C0bmVPHdk8QHYt2_k");
			x.add("response", recaptcha);

			RecaptchaResponse captchaResp = restTemplate.postForObject("https://www.google.com/recaptcha/api/siteverify", x, RecaptchaResponse.class);
			if (!captchaResp.isSuccess()) {
				redir.addAttribute("captcha_error", "true");
				t.setResult("redirect:/register");
				return;
			}

			Set<RegistrationError> errors = registrationValidator.validate(form);
			if (errors.isEmpty()) {
				String pass = form.getPassword();
				int credits = 0;
				int dollarAmount = 0;

				int accId = db.queryForObject("INSERT INTO accounts (username, password, email, displayname, credits, donated) VALUES (?, ?, ?, ?, ?, ?) RETURNING id", Integer.class, form.getUsername().toLowerCase(), pass, form.getEmail(), form.getUsername(), credits, dollarAmount);

				if (accId > 0) {
					mixPanelService.event("Register", ip);

					List<Event> events = new ArrayList<>();
					long unixTime = System.currentTimeMillis() / 1000L;
					UserData userData_0 = new UserData()
							.emails(Collections.singletonList(form.getEmail()))
							.clientIpAddress(ip)
							.countryCode(Sessions.getCountry(request))
							.clientUserAgent(Sessions.getUserAgent(request));

					com.facebook.ads.sdk.serverside.Event event_0 = new com.facebook.ads.sdk.serverside.Event()
							.eventName("Register")
							.eventTime(unixTime)
							.userData(userData_0)
							.actionSource(ActionSource.website)
							.eventSourceUrl("https://enchanta.net/register");
					events.add(event_0);

					EventRequest eventRequest = new EventRequest(PIXEL_ID, getContext())
							.data(events);

					try {
						eventRequest.execute();
					} catch (APIException e) {
						e.printStackTrace();
					}
				}

				redir.addAttribute("success", accId > 0);
				t.setResult("redirect:/register");
				return;
			}

			for (RegistrationError error : errors) {
				redir.addAttribute(error.name().toLowerCase(), "true");
			}

			redir.addAttribute("success", false);
			redir.addAttribute("errors", errors.stream().map(RegistrationError::getMessage).toArray());

			t.setResult("redirect:/register");
		});

		return t;
	}


	@RequestMapping(value = "/register/taken/{name}")
	public @ResponseBody String taken(@PathVariable("name") String name, HttpServletResponse response) {
		try {
			int c = db.queryForObject("SELECT count(1) FROM accounts WHERE LOWER(displayname) = ?", Integer.class, name.toLowerCase());
			response.setStatus(c == 0 ? 404 : 200);
			return "";
		} catch (Exception ignored) {
			response.setStatus(404);
			return "";
		}
	}

	@RequestMapping(value = "/register/email/{email:.*}")
	public @ResponseBody String emailTaken(@PathVariable("email") String email, HttpServletResponse response) {
		try {
			int c = db.queryForObject("SELECT count(1) FROM accounts WHERE LOWER(email) = ?", Integer.class, email.toLowerCase());
			response.setStatus(c == 0 ? 404 : 200);
			return "";
		} catch (Exception ignored) {
			response.setStatus(404);
			return "";
		}
	}
}
