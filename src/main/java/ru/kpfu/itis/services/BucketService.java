package ru.kpfu.itis.services;

import ru.kpfu.itis.models.User;

import javax.servlet.http.Cookie;

public interface BucketService {
    void saveToDB(Cookie[] cookies, Long productId);
}
