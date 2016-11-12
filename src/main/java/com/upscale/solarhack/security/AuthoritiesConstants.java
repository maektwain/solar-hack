package com.upscale.solarhack.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String VENDOR = "ROLE_VENDOR";

    public static final String INVESTOR = "ROLE_INVESTOR";

    private AuthoritiesConstants() {
    }
}
