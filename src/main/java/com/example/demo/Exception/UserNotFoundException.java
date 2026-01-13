package com.example.demo.Exception;

public class UserNotFoundException extends Exception{

	private static final long serialVersionUID = -6765584789440403032L;
	
	public UserNotFoundException(String msg) {
		super(msg);
	}

}
