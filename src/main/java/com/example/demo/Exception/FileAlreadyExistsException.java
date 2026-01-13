package com.example.demo.Exception;

public class FileAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -8908886726329346381L;
	
	public FileAlreadyExistsException(String msg) {
		super(msg);
	}

}
