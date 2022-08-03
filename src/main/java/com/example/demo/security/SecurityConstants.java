package com.example.demo.security;

public class SecurityConstants {

    //Constants for internal app usage, secret could be retrieved on configuration from external source
    public static final String SECRET = "edeleonsecretkey";
    public static final long EXPIRATION_TIME= 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/create";
}
