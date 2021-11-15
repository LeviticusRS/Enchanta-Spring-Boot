package com.joshua.ransom.model;

import com.google.common.base.MoreObjects;

/**
 * Created by Joshua Ransom on 3/1/2020.
 */
public class Purchase {

	private String name;
	private String type;
	private double price;
	private int id;
	private int amount;

	public Purchase(String name, String type, double price, int id, int amount) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.id = id;
		this.amount = amount;
	}

	public String name() {
		return name;
	}

	public String type() {
		return type;
	}

	public int id() {
		return id;
	}

	public int amount() {
		return amount;
	}

	public double price() {
		return price;
	}

	@Override public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("type", type)
				.add("price", price).add("id", id).add("amount", amount).toString();
	}
}
