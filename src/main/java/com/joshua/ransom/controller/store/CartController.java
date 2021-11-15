package com.joshua.ransom.controller.store;

import com.joshua.ransom.Website;
import com.joshua.ransom.model.Account;
import com.joshua.ransom.model.Cart;
import com.joshua.ransom.model.Product;
import com.joshua.ransom.service.MixPanelService;
import com.joshua.ransom.util.Sessions;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/store/cart")
public class CartController {

	final
	Environment env;

	private final JdbcTemplate db;
	final
	MixPanelService mixPanelService;

	public CartController(Environment env, JdbcTemplate db, MixPanelService mixPanelService) {
		this.env = env;
		this.db = db;
		this.mixPanelService = mixPanelService;
	}

	@PostMapping(value = "/add")
	public String addToCart(@RequestParam int product, @RequestParam int amount, HttpServletRequest request) {
		mixPanelService.event("Cart Add", Sessions.getClientIp(request));
		int accountId = Integer.parseInt(Sessions.current().getAttribute("account").toString());

		// Limit quantity to 250 per query..
		amount = Math.min(250, Math.max(0, amount));

		// Make sure the product exists
		int updated = db.update("UPDATE cart_items SET quantity = quantity + ? WHERE product_id = ? AND owner_id = ?",
				amount, product, accountId);

		// If not updated, insert.
		if (updated < 1) {
			db.update("INSERT INTO cart_items (owner_id, product_id, quantity) VALUES (?,?,?)", accountId, product, amount);
		}
		return "redirect:/store";
	}

	@PostMapping(value = "/remove")
	public String removeFromCart(@RequestParam int product, @RequestParam int amount) {
		int accountId = Integer.parseInt(Sessions.current().getAttribute("account").toString());

		// Limit quantity to 2500 per query..
		amount = Math.min(2500, Math.max(0, amount));

		// Make sure the product exists
		db.update("UPDATE cart_items SET quantity = quantity - ? WHERE product_id = ? AND owner_id = ? AND quantity > 0", amount, product, accountId);
		db.update("DELETE FROM cart_items WHERE owner_id = ? AND quantity < 1", accountId);

		return "redirect:/store";
	}

	@RequestMapping(value = "/clear")
	public String clearCart() {
		int accountId = Integer.parseInt(Sessions.current().getAttribute("account").toString());
		db.update("DELETE FROM cart_items WHERE owner_id = ?", accountId);
		return "redirect:/store";
	}

	@RequestMapping(value = "/removeall/{id}")
	public String clearCart(@PathVariable int id) {
		int accountId = Integer.parseInt(Sessions.current().getAttribute("account").toString());
		db.update("DELETE FROM cart_items WHERE owner_id = ? AND product_id = ?", accountId, id);
		return "redirect:/store";
	}

	@RequestMapping(value = "/checkout")
	public String completeCheckout(HttpServletRequest request) {
		int accountId = Integer.parseInt(Sessions.current().getAttribute("account").toString());
		List<Map<String, Object>> items;
		if (accountId > 0) {
			Cart cart = new Cart(db, Account.findByAccountId(db, accountId));
			Account account = cart.getAccount();
			int creditsPrice = cart.totalPrice();
			if (creditsPrice > 0) {
				if (account.credits() >= cart.totalPrice()) {
					int worked = db.update("UPDATE accounts SET credits = ? WHERE id = ?", account.credits() - cart.totalPrice(), accountId);
					if (worked > 0) {
						for (Product product : cart.products()) {
							db.update("INSERT INTO credit_transactions (ip, account_id, product_id, quantity) " +
											"VALUES (?, ?, ?, ?)",
									Sessions.getClientIp(request), accountId, product.id(), product.quantity());
							db.update("INSERT INTO donator_boss_claims (account_id, credits) " +
									"VALUES (?, ?)", accountId, cart.totalPrice());
							Website.cartDiscord().send("Cart Checkout: [" + account.displayName() + "] " + product.name());
						}
					} else {
						db.update("DELETE FROM cart_items WHERE owner_id = ?", accountId);
						return "redirect:/store";
					}
				} else {
					db.update("DELETE FROM cart_items WHERE owner_id = ?", accountId);
					return "redirect:/store";
				}
			}
		}
		db.update("DELETE FROM cart_items WHERE owner_id = ?", accountId);
		return "redirect:/store";
	}

}