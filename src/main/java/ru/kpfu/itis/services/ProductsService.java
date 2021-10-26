package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;

import java.util.List;

public interface ProductsService {
    void add(ProductForm productForm);
    void addToBucket(Long userId, Long productId);
    void addToFavourites(Long userId, Long productId);
    List<Product> findFavouriteProductsByUserId(Long userId);
    List<Product> findInBucketByUserId(Long userId);
    List<Product> findAll();
    void removeFromBucket(Long userId, Long productId);
    void removeFromFavourites(Long userId, Long productId);
}
