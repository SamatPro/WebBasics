package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;

import java.util.List;

public interface ProductsService {
    void add(ProductForm productForm);
    List<Product> findAll();
    void addToFavorite(Long userId, Long productId);
    void addToBusket(Long userId, Long productId);
    List<Product> allInBusketById(Long userId);
}
