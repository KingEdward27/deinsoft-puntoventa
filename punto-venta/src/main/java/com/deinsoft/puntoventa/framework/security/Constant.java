package com.deinsoft.puntoventa.framework.security;

public class Constant {
	
	public static final String LOGIN_URL = "/login";
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer";
	
	public static final String ADMIN = "admin";
	
	public static final String ISSUER_INFO = "DEINSOFTCLOUD";
	public static final String SUPER_SECRET_KEY = "C0mpl1c4d0.";
	public static final long TOKEN_EXPIRATION_TIME = 30; // minutes
	public static final long TOKEN_REFRESH_EXPIRATION_TIME = 180;

}
