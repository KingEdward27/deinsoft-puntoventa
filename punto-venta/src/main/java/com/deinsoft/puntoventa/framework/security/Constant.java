package com.deinsoft.puntoventa.framework.security;

public class Constant {
	
	public static final String LOGIN_URL = "/login";
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer";
	
	public static final String ADMIN = "admin";
	
	public static final String ISSUER_INFO = "DEINSOFTCLOUD";
	public static final String SUPER_SECRET_KEY = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";
	public static final long TOKEN_EXPIRATION_TIME = 30; // minutes
	public static final long TOKEN_REFRESH_EXPIRATION_TIME = 180;

}
