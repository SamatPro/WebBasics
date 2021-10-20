package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;

import java.util.List;

public interface ProductsService {
    void add(ProductForm productForm);
    List<Product> findAll();
    void addToFavoriteProduct(Long userId, Long productId);
    void addToBucket(Long userId, Long productId);
    List<Product> findAllInBucketById(Long userId);
    List<Product> FindAllInFavouritesById(Long userId);
}
