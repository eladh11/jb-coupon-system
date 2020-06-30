package com.couponssystem.exception;

public class AlreadyExitException extends Exception {

	public AlreadyExitException() {
		super("Already Exit...");
	}

	public AlreadyExitException(String massage) {
		System.out.println(massage);

	}
}
