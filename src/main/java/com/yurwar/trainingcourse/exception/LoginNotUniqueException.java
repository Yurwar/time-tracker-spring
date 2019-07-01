package com.yurwar.trainingcourse.exception;

public class LoginNotUniqueException extends RuntimeException {
    public LoginNotUniqueException() {
    }

    public LoginNotUniqueException(String message) {
        super(message);
    }

    public LoginNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginNotUniqueException(Throwable cause) {
        super(cause);
    }

    public LoginNotUniqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
