package com.joshua.ransom.model;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class Cart {

	private final List<Product> products;

	private final JdbcTemplate db;

	private final Account account;

	public Cart(JdbcTemplate db, Account account) {
		this.db = db;
		this.account = account;

		products = db.query("SELECT * FROM\n" +
				"  (SELECT cart_items.product_id, sum(quantity) AS total_quantity FROM cart_items\n" +
				"  WHERE owner_id = ? AND quantity > 0 GROUP BY product_id) AS aggregate_cart\n" +
				"LEFT JOIN shop_products ON shop_products.id = aggregate_cart.product_id", new Product.Mapper(), account.id());
	}

	public List<Product> products() {
		return products;
	}

	/**
	 * Seals and removes all things associated with this cart. Done after an invoice has been generated.
	 */
	public void seal() {
		db.update("DELETE FROM cart_items WHERE owner_id = ?", account.id());
	}

	public int totalPrice() {
		int d = 0;
		for (Product p : products) {
			if (p.discounted()) {
				d += (p.price() - p.discountPrice()) * p.quantity();
			} else {
				d += p.price() * p.quantity();
			}
		}
		return d;
	}

	public double originalPrice() {
		double d = 0;
		for (Product p : products) {
			d += p.price() * p.quantity();
		}
		return d;
	}

	public Account getAccount() {
		return account;
	}
}