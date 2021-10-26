package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Product;

import java.time.LocalDate;
import java.util.List;

public interface ProductsRepository extends CrudRepository<Product> {
    List<Product> findFavouriteProductsByUserId(Long userId);
    List<Product> findProductsInBucketByUserId(Long userId);
    void addProductToFavourite(Long userId, Long productId);
    void addProductToBusket(Long userId, Long productId);
    void removeFromBucket(Long userId, Long idToRemove);
    void removeFromFavourite(Long userID, Long idToRemove);
}
