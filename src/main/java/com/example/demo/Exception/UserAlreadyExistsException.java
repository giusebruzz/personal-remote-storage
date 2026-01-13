package com.example.demo.Exception;

public class UserAlreadyExistsException extends Exception{

	private static final long serialVersionUID = -8503383360656108281L;
	
	public UserAlreadyExistsException(String msg) {
		super(msg);
	}
	

}
