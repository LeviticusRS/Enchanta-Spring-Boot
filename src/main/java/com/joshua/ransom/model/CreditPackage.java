package com.joshua.ransom.model;

import java.util.Optional;

public enum CreditPackage {

	PACKAGE_1(100000 + 50, 500, 0, 5.00, "500 Credits with 0 Bonus", "credits-1"),
	PACKAGE_2(100000 + 105, 1000, 100, 10.00, "1,000 Credits with 100 Bonus", "credits-5"),
	PACKAGE_3(100000 + 210, 2500, 300, 25.00, "2,500 Credits with 300 Bonus", "credits-2"),
	PACKAGE_4(100000 + 340, 5000, 700, 50.00, "5,000 Credits with 700 Bonus", "credits-6"),
	PACKAGE_5(100000 + 500, 15000, 2500, 150.00, "5,000 Credits with 1,500 Bonus", "credits-3"),
	PACKAGE_6(100000 + 800, 25000, 4000, 250.00, "25,000 Credits with 4,000 Bonus", "credits-4"),
	PACKAGE_7(100000 + 900, 50000, 10000, 500.00, "50,000 Credits with 10,000 Bonus", "credits-4"),
	PACKAGE_8(100000 + 920, 100000, 22000, 1000.00, "100,000 Credits with 22,000 Bonus", "credits-4"),
	//PACKAGE_9(100000 + 69, 0, 0, 2000.00, "Custom", "credits-4"),
	;

	private final int bonus;
	private final int productId;
	private final int credits;
	private final double price;
	private final String name;
	private final String image;

	CreditPackage(int productId, int credits, int bonus, double price, String name, String image) {
		this.productId = productId;
		this.credits = credits;
		this.price = price;
		this.name = name;
		this.image = image;
		this.bonus = bonus;
	}

	public int productId() {
		return productId;
	}

	public String image() {
		return image;
	}

	public int credits() {
		return credits;
	}

	public double price() {
		return price;
	}

	public String packageName() {return name;}

	public static Optional<CreditPackage> forProduct(int productId) {
		for (CreditPackage pkg : values()) {
			if (pkg.productId == productId) {
				return Optional.of(pkg);
			}
		}

		return Optional.empty();
	}

	public int bonus() {
		return bonus;
	}

	public int totalCredits(String paymentType) {
		switch(paymentType) {
			case "PAYPAL":
				return credits + bonus;
			case "STRIPE":
				return (int) ((credits/* * 1.10*/) + bonus);
			case "CRYPTO":
				return (int) ((credits/* * 1.15*/) + bonus);
		}
		return credits + bonus;
	}

	@Override
	public String toString() {
		return "CreditPackage{" +
				"credits=" + credits +
				", price=" + price +
				", name='" + name + '\'' +
				'}';
	}
}