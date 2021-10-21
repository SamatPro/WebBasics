package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Bucket;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.AuthRepostoryImpl;
import ru.kpfu.itis.repositories.BucketRepository;
import ru.kpfu.itis.repositories.BucketRepositoryImpl;

import javax.servlet.http.Cookie;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Optional;

public class BucketServiceImpl implements BucketService {
    private BucketRepository bucketRepository;
    private AuthRepository authRepository;

    public BucketServiceImpl(Connection connection) {
        bucketRepository = new BucketRepositoryImpl(connection);
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
        Bucket bucket = null;
        if (user != null) {
            bucket = new Bucket(user.getId(), productId);
        }
        bucketRepository.save(bucket);
    }
}
