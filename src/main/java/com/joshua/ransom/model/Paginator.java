package com.joshua.ransom.model;

/**
 * Created by Joshua Ransom on 7/20/2020.
 */
public class Paginator {

	private int current;
	private int pageSize;
	private int count;

	public Paginator(int current, int pageSize, int count) {
		this.current = Math.max(1, current);
		this.pageSize = pageSize;
		this.count = count;
	}

	public int getCurrent() {
		return current;
	}

	public int getSkip() {
		return (current - 1) * pageSize;
	}

	public int getLastPage() {
		return (count + (pageSize - 1)) / pageSize;
	}

	public boolean isRightArrowVisible() {
		return current < getLastPage();
	}

	public boolean isLeftArrowVisible() {
		return current > 1;
	}

}
