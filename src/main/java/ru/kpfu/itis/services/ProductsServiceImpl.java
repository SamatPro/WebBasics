package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.ProductForm;
import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.Product;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.ProductsRepository;

import javax.servlet.http.Cookie;
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
    public void addToFavourite(Long userId, Long productId) {
        if(!isAlreadyInFavourite(userId, productId)) {
            productsRepository.addToFavourite(userId, productId);
        }
    }

    private boolean isAlreadyInFavourite(Long userId, Long productId){
        return productsRepository.isAlreadyInFavourite(userId, productId);
    }

    @Override
    public Long getUserID(Cookie[] cookies) {
        for (Cookie cookie: cookies) {
            if(cookie.getName().equals("auth")){
                Auth auth = authRepository.findByCookieValue(cookie.getValue());
                return auth.getUser().getId();
            }
        }
        return null;
    }

    @Override
    public void addToBucket(Long userId, Long productId) {
        if(!isAlreadyInBucket(userId, productId)){
            productsRepository.addToBucket(userId, productId);
        }
    }

    @Override
    public List<Product> findFavourites(Long userId) {
        return productsRepository.findFavouriteProductsByUserId(userId);
    }

    @Override
    public List<Product> findBucket(Long userId) {
        return productsRepository.findProductsInBucketByUserId(userId);
    }

    @Override
    public void deleteFromBucket(Long userId, Long productId) {
        if(isAlreadyInBucket(userId, productId)){
            productsRepository.removeFromBucket(userId, productId);
        }
    }
    @Override
    public void deleteFromFavourites(Long userId, Long productId) {
        if(isAlreadyInFavourite(userId, productId)){
            productsRepository.removeFromFavourites(userId, productId);
        }
    }

    private boolean isAlreadyInBucket(Long userId, Long productId) {
        return productsRepository.isAlreadyInBucket(userId, productId);
    }
}
