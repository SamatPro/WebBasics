package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.FavouriteProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FavouriteProductRepoImpl implements FavouriteProductRepository {
    private Connection connection;
    //language=sql
    private final String SAVE_FAVOURITE_PRODUCT_SQL = "INSERT INTO favourite_products(user_id, product_id) VALUES (?, ?)";

    public FavouriteProductRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<FavouriteProduct> findAll() {
        return null;
    }

    @Override
    public Optional<FavouriteProduct> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public FavouriteProduct save(FavouriteProduct favouriteProduct) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SAVE_FAVOURITE_PRODUCT_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preparedStatement == null) {
            return null;
        }
        try {
            preparedStatement.setLong(1, favouriteProduct.getUserId());
            preparedStatement.setLong(2, favouriteProduct.getProductId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favouriteProduct;
    }

    @Override
    public void deleteById(Long id) {

    }
}
