package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryImpl implements ProductsRepository {

    private Connection connection;

    //language=sql
    private final String INSERT_PRODUCT = "INSERT INTO products(title, cost, description) VALUES (?, ?, ?)";
    //language=sql
    private final String INSERT_PRODUCT_INTO_FAVOURITE = "INSERT INTO favourites(user_id, product_id) values (?,?) on conflict do nothing";
    //language=sql
    private final String INSERT_PRODUCE_INTO_BUCKET = "INSERT INTO bucket(user_id, product_id) values (?,?) on conflict do nothing";

    private final String FIND_FAVOURITE_PRODUCTS_BY_USER_ID = "SELECT * FROM products p INNER JOIN favourites f ON p.id = f.product_id INNER JOIN users ON f.user_id=users.id WHERE user_id=?;";
    private final String FIND_PRODUCTS_IN_BUCKET_BY_USER_ID = "SELECT * FROM products p INNER JOIN bucket b ON p.id = b.product_id INNER JOIN users ON b.user_id=users.id WHERE user_id=?;";

    private final String FIND_ALL = "SELECT * FROM products;";

    public ProductsRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            return rowMapProducts.rowMap(resultSet);
        } catch (SQLException ignored) {
        }
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Product product) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT);
            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setDouble(2, product.getCost());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void deleteById(Long id) {

    }

    private final RowMapper<Product> rowMapper = ((resultSet) -> {
        if (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setTitle(resultSet.getString("title"));
            product.setCost(resultSet.getDouble("cost"));
            product.setDescription(resultSet.getString("description"));
            return product;
        } else {
            return null;
        }
    });

    @Override
    public List<Product> findFavouriteProductsByUserId(Long userId) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_FAVOURITE_PRODUCTS_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            return rowMapProducts.rowMap(resultSet);
        } catch (SQLException ignored) { }
        return null;
    }

    @Override
    public List<Product> findProductsInBucketByUserId(Long userId) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCTS_IN_BUCKET_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            return rowMapProducts.rowMap(resultSet);
        } catch (SQLException ignored) {
        }
        return null;
    }

    @Override
    public void addProductToFavourites(Long userId, Long productId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_INTO_FAVOURITE);
            preparedStatement.setLong(1,userId);
            preparedStatement.setLong(2,productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void addProductToBucket(Long userId, Long productId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCE_INTO_BUCKET);
            preparedStatement.setLong(1,userId);
            preparedStatement.setLong(2,productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final RowMapper<List<Product>> rowMapProducts = ((resultSet) -> {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setTitle(resultSet.getString("title"));
            product.setCost(resultSet.getDouble("cost"));
            product.setDescription(resultSet.getString("description"));
            products.add(product);
        }
        return products;
    });


}
