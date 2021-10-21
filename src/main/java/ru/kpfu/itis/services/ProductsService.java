package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;

import javax.servlet.http.Cookie;
import java.util.List;

public interface ProductsService {
    void add(ProductForm productForm);
    List<Product> findAll();
    void addingToFavorite(Long userId, Long productId);
    void addingToBucket(Long userId, Long productId);
    List<Product> allInBucketById(Long userId);
    List<Product> allInFavouritesById(Long userId);
}
