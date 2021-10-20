package ru.kpfu.itis.utils;

import javax.servlet.http.Cookie;
import java.util.Optional;

public class CookieHandler {
    public static Optional<Cookie> findCookieByName(Cookie[] cookies, String name){
        for (Cookie cookie: cookies){
            if (cookie.getName().equals(name)) return Optional.of(cookie);
        }
        return Optional.empty();
    }
}
