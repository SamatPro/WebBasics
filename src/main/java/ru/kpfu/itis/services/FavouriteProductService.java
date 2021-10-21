package ru.kpfu.itis.services;

import javax.servlet.http.Cookie;

public interface FavouriteProductService {
    void saveToDB(Cookie[] cookies, Long productId);
}
