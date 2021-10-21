package ru.kpfu.itis.models;

public class Bucket {
    private Long userId;
    private Long productId;

    public Bucket(Long userId, Long productId) {
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
