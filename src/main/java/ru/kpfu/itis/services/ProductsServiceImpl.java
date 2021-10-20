package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.repositories.ProductsRepository;

import java.util.List;

public class ProductsServiceImpl implements ProductsService {

    private ProductsRepository productsRepository;

    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
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
    public void addProductToFavourites(Long userId, Long productId) {
        productsRepository.addProductToFavourites(userId,productId);
    }

    @Override
    public void addProductToBucket(Long userId, Long productId) {
        productsRepository.addProductToBucket(userId, productId);
    }

    @Override
    public List<Product> findFavouritesByUserId(Long userId) {
        return productsRepository.findFavouriteProductsByUserId(userId);
    }

    @Override
    public List<Product> findBucketByUserId(Long userId) {
        return productsRepository.findProductsInBucketByUserId(userId);
    }
}
