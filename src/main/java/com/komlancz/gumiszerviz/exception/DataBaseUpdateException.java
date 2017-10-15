package com.komlancz.gumiszerviz.exception;

public class DataBaseUpdateException extends Exception {
    public DataBaseUpdateException(String message) {
        super(message);
    }

    public DataBaseUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
