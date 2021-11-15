package com.joshua.ransom.controller.store.stripe;

import com.joshua.ransom.model.BondPackage;
import com.joshua.ransom.model.CreditPackage;
import com.joshua.ransom.util.Sessions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class CheckoutController {
 
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    private final JdbcTemplate db;

    public CheckoutController(JdbcTemplate db) {
        this.db = db;
    }

    @RequestMapping("/credits/stripe/checkout")
    public String checkout(Model model) {
        // If we're not logged in, take us to the login form.
        if (!Sessions.has()) {
            return "redirect:/login";
        }

        int accountId = Integer.parseInt(Sessions.current().getAttribute("account").toString());

        Map<String, Object> account = db.queryForMap("SELECT * FROM accounts WHERE id = ?;", accountId);

        // Block missing email
        if (account.get("email") == null || account.get("email").equals("invalid@email.com")) {
            return "redirect:/email";
        }

        // Store the points in the model map
        model.addAttribute("packages", CreditPackage.values());
        model.addAttribute("bondPackages", BondPackage.values());
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "credits";
    }
}