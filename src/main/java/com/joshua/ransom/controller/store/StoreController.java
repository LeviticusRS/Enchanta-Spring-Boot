package com.joshua.ransom.controller.store;

import com.joshua.ransom.model.Account;
import com.joshua.ransom.model.Cart;
import com.joshua.ransom.util.Sessions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class StoreController {

    private final JdbcTemplate db;

    public StoreController(JdbcTemplate db) {
        this.db = db;
    }

    @RequestMapping(value = "/store")
	public String credits(Map<String, Object> model) {
        if (!Sessions.has()) {
            return "redirect:/login";
        }

        Account account = Account.populateView(db);
        List<Map<String, Object>> products = db.queryForList("SELECT * FROM shop_products WHERE shop_products.disabled = FALSE ORDER BY sort_order ASC;");
        List<Map<String, Object>> pending = db.queryForList("SELECT credit_transactions.*, shop_products.name, shop_products.item_amount " +
                "FROM credit_transactions LEFT JOIN shop_products ON product_id=shop_products.id " +
                "WHERE account_id = ? AND acquired = FALSE;", account.id());

        // Store the points in the model map
        model.put("products", products);
        model.put("pending", pending);
        model.put("cart", new Cart(db, account));
		return "store";
	}
}
