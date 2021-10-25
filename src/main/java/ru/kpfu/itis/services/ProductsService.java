package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;

import javax.servlet.http.Cookie;
import java.util.List;

public interface ProductsService {
    void add(ProductForm productForm);

    public void addToBucket(Long userId, Long productId);

    public void addToFavourites(Long userId, Long productId);

    List<Product> findAll();

    List<Product> findFavouriteProductsByUserId(Long userId);

    List<Product> findInBucketByUserId(Long userId);

}
