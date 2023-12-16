package com.storm.test.exceptions;

public class CantDeleteException extends RuntimeException{

    public CantDeleteException(String msg){
        super(msg);
    }
}
