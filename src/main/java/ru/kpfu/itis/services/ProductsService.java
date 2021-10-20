package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;

import java.util.List;

public interface ProductsService {
    void add(ProductForm productForm);
    List<Product> findAll();
    void addProductToFavourites(Long userId,Long productId);
    void addProductToBucket(Long userId,Long productId);
    List<Product> findFavouritesByUserId(Long userId);
    List<Product> findBucketByUserId(Long userId);
}
