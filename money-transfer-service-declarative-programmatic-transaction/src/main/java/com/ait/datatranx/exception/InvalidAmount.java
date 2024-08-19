package com.ait.datatranx.exception;

public class InvalidAmount extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidAmount() {

	}

	public InvalidAmount(String msg) {
		super(msg);
	}
}
