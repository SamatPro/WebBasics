package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Product;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Product> {
    List<Product> findFavouriteProductsByUserId(Long userId);
    List<Product> findProductsInBucketByUserId(Long userId);
    void addToFav(Long userId, Long productId);
    void addToBucket(Long userId, Long productId);
    void removeFromBucket(Long userId, Long productId);
    void removeFromFavourites(Long userId, Long productId);
}
