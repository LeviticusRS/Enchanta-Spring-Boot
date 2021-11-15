package com.joshua.ransom.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Joshua Ransom on 3/1/2020.
 */
public class Product {

	private int id;
	private String name;
	private String description;
	private double price;
	private String image;
	private int itemId;
	private int itemAmount;
	private int quantity;
	private double discountPrice;

	public int id() {
		return id;
	}

	public String name() {
		return name;
	}

	public String description() {
		return description;
	}

	public double price() {
		return price;
	}

	public double discountPrice() {
		return discountPrice;
	}

	public boolean discounted() {
		return discountPrice > 0 && discountPrice < price;
	}

	public String image() {
		return image;
	}

	public int itemId() {
		return itemId;
	}

	public int itemAmount() {
		return itemAmount;
	}

	public int quantity() {
		return quantity;
	}

	public static final class Mapper implements RowMapper<Product> {

		@Override public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product();
			product.id = rs.getInt("id");
			product.price = rs.getInt("price");
			product.name = rs.getString("name");
			product.discountPrice = rs.getDouble("discount_price");
			product.quantity = rs.getInt("total_quantity");
			product.image = rs.getString("image");
			product.description = rs.getString("description");
			return product;
		}

	}

}
