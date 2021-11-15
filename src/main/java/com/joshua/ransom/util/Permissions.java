package com.joshua.ransom.util;

/**
 * Created by Joshua Ransom on 3/3/2020.
 */
public class Permissions {

	private static final int ACCOUNT_BART = 3875;
	private static final int ACCOUNT_SITUATIONS = 3877;

	public static boolean mayAccessFinancials(int account) {
		return account == ACCOUNT_BART || account == ACCOUNT_SITUATIONS;
	}

}
