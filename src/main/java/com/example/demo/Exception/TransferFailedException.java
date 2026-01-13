package com.example.demo.Exception;

public class TransferFailedException extends Exception{

	private static final long serialVersionUID = -5286055327342222244L;
	
	public TransferFailedException(String msg) {
		super(msg);
	}

}
