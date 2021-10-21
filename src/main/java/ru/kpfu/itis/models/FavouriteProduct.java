package ru.kpfu.itis.models;

public class FavouriteProduct {
    private Long userId;
    private Long productId;

    public FavouriteProduct(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }
}
