package com.krishnendu.projectshareplussvc.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    @Value("${ACCESS_TOKEN_NAME}")
    private String accessTokenCookieName;

    @Value("${ACCESS_TOKEN_VALIDITY}")
    private String accessTokenCookieValidity;


    public HttpCookie createAccessTokenCookie(String token) {
        return ResponseCookie.from(accessTokenCookieName, token)
                .maxAge(3600 * Integer.parseInt(accessTokenCookieValidity))
                .httpOnly(true)
                .path("/")
                .build();
    }

    public HttpCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(accessTokenCookieName, "").maxAge(0).httpOnly(true).path("/").build();
    }
}
