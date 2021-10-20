package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.ProductsRepository;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.List;

public class ProductsServiceImpl implements ProductsService {

    private ProductsRepository productsRepository;
    private AuthRepository authRepository;

    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public ProductsServiceImpl(ProductsRepository productsRepository, AuthRepository authRepository) {
        this.productsRepository = productsRepository;
        this.authRepository = authRepository;
    }

    @Override
    public void add(ProductForm productForm) {
        Product product = new Product();
        product.setTitle(productForm.getTitle());
        product.setCost(productForm.getCost());
        product.setDescription(productForm.getDescription());
        productsRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public void addingToFavorite(Long userId, Long productId) {
        productsRepository.addProductToFavourite(userId, productId);
    }

    @Override
    public void addingToBucket(Long userId, Long productId) {
        productsRepository.addProductToBucket(userId, productId);
    }

    @Override
    public List<Product> allInBucketById(Long userId) {
        return productsRepository.findProductsInBucketByUserId(userId);
    }

    @Override
    public List<Product> allInFavouritesById(Long userId) {
        return productsRepository.findFavouriteProductsByUserId(userId);
    }


}
