package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Bucket;
import ru.kpfu.itis.models.FavouriteProduct;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.*;

import javax.servlet.http.Cookie;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Optional;

public class FavouriteProductServiceImpl implements FavouriteProductService{
    private FavouriteProductRepository favouriteProductRepository;
    private AuthRepository authRepository;

    public FavouriteProductServiceImpl(Connection connection) {
        favouriteProductRepository = new FavouriteProductRepoImpl(connection);
        authRepository = new AuthRepostoryImpl(connection);
    }

    @Override
    public void saveToDB(Cookie[] cookies, Long productId) {
        Optional<Cookie> userCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("auth"))
                .findFirst();
        User user = null;
        if (userCookie.isPresent()) {
            user = authRepository.findByCookieValue(userCookie.get().getValue()).getUser();
        }
        FavouriteProduct favouriteProduct = null;
        if (user != null) {
            favouriteProduct = new FavouriteProduct(user.getId(), productId);
        }
        favouriteProductRepository.save(favouriteProduct);
    }
}
