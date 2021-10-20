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
    public void addToFavoriteProduct(Long userId, Long productId) {
        productsRepository.addProductToFavourite(userId, productId);
    }

    @Override
    public void addToBucket(Long userId, Long productId) {
        productsRepository.addProductToBucket(userId, productId);
    }

    @Override
    public List<Product> findAllInBucketById(Long userId) {
        return productsRepository.findProductsInBucketByUserId(userId);
    }

    @Override
    public List<Product> FindAllInFavouritesById(Long userId) {
        return productsRepository.findFavouriteProductsByUserId(userId);
    }
}
