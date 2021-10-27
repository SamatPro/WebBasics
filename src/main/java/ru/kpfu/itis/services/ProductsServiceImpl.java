package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.repositories.ProductsRepository;

import java.util.List;

public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

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
    public void addToBucket(Long userId, Long productId) {
        productsRepository.addToBucket(userId, productId);
    }
    @Override
    public void addToFavourites(Long userId, Long productId) {
        productsRepository.addToFav(userId, productId);
    }
    @Override
    public List<Product> user_bucket(Long userId) {
        return productsRepository.findProductsInBucketByUserId(userId);
    }
    @Override
    public void removeFromBucket(Long userId, Long idToRemove) {
        productsRepository.removeFromBucket(userId, idToRemove);
    }

    @Override
    public void removeFromFavourites(Long userId, Long idToRemove) {
        productsRepository.removeFromFavourites(userId, idToRemove);
    }



}
