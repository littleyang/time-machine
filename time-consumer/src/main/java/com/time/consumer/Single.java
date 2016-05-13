package com.time.consumer;

public class Single {

	private static Single instance = null;

	protected Single() {

	}

	public static Single getInstance() {
		if (instance == null) {
			instance = new Single();
		}
		return instance;
	}
}