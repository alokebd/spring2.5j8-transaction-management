package com.ait.datatranx.exception;

import java.util.NoSuchElementException;

public class ResourceNotFoundException  extends NoSuchElementException{
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		
	}
	public ResourceNotFoundException(String msg){
        super(msg);
    }
}
