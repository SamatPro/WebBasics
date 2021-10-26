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
    public void addToFavorite(Long userId, Long productId) {
        productsRepository.addProductToFavourite(userId, productId);
    }

    @Override
    public void addToBusket(Long userId, Long productId) {
        productsRepository.addProductToBusket(userId, productId);
    }

    @Override
    public List<Product> allInBusketById(Long userId) {
       return productsRepository.findProductsInBucketByUserId(userId);
    }

    @Override
    public void removeFromBucket(Long userId, Long idToRemove) {
        productsRepository.removeFromBucket(userId, idToRemove);
    }

    @Override
    public void removeFromFavourites(Long userId, Long idToRemove) {
        productsRepository.removeFromFavourite(userId, idToRemove);
    }
}
