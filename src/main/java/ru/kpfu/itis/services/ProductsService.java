package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;

import java.util.List;

public interface ProductsService {
    void add(ProductForm productForm);
    List<Product> findAll();
}
