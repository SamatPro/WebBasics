package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Product;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Product> {
    List<Product> findFavouriteProductsByUserId(Long userId);
    List<Product> findProductsInBucketByUserId(Long userId);

    public void addToBucket(Long userId, Long productId);
    public void addToFavourites(Long userId, Long productId);

    public void removeFromBucket(Long userId, Long productId);
    public void removeFromFavourites(Long userId, Long productId);
}
