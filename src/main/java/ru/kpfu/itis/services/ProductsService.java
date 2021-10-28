package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;

import java.util.List;

public interface ProductsService {
    void add(ProductForm productForm);

    List<Product> findAll();

    void addToBucket(Long userId, Long productId);

    void addToFavourites(Long userId, Long productId);

    List<Product> user_bucket(Long id);

    void removeFromBucket(Long userId, Long idToRemove);

    void removeFromFavourites(Long userId, Long idToRemove);

}
