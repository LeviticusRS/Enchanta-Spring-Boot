package com.joshua.ransom.model;

import java.util.Optional;

public enum BondPackage {

	PACKAGE_25(100000 + 100, 5, 0, 5.00, "5$ Bond", "bond"),
	PACKAGE_85(100000 + 220, 10, 0, 10.00, "10$ Bond", "bond"),
	PACKAGE_175(100000 + 420, 25, 0, 25.00, "25$ Bond", "bond"),
	PACKAGE_285(100000 + 780, 50, 0, 50.00, "50$ Bond", "bond"),
	PACKAGE_11520(100000 + 1000, 150, 0, 150.00, "150$ Bond", "bond"),
	PACKAGE_12520(100000 + 1600, 250, 0, 250.00, "250$ Bond", "bond"),
	;

	private final int bonus;
	private final int productId;
	private final int credits;
	private final double price;
	private final String name;
	private final String image;

	BondPackage(int productId, int credits, int bonus, double price, String name, String image) {
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

	public static Optional<BondPackage> forProduct(int productId) {
		for (BondPackage pkg : values()) {
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
		return "BondPackage{" +
				"price=" + price +
				", name='" + name + '\'' +
				'}';
	}
}