package com.study.shop.exception;

import lombok.Getter;

@Getter
public class LoginException extends RuntimeException {
    String url;

    public LoginException(String message, String url) {
        super(message);
        this.url = url;
    }
}
