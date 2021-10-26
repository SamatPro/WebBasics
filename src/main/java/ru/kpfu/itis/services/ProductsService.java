package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;

import javax.servlet.http.Cookie;
import java.util.List;

public interface ProductsService {
    void add(ProductForm productForm);
    List<Product> findAll();

    void addToFavourite(Long userId, Long productId);

    Long getUserID(Cookie[] cookies);

    void addToBucket(Long userId, Long productId);

    List<Product> findFavourites(Long userId);
    List<Product> findBucket(Long userId);

    void deleteFromFavourites(Long userId, Long productId);
    void deleteFromBucket(Long userId, Long productId);
}
