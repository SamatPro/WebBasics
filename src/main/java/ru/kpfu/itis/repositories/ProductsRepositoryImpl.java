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
    private final String DELETE_PRODUCT_BUCKET = "DELETE FROM bucket b where b.user_id = ? and b.product_id = ?";
    private final String DELETE_PRODUCT_FAVOURITE = "DELETE FROM favourite_products f where f.user_id = ? and f.product_id = ?";
    private final String INSERT_PRODUCT = "INSERT INTO products(title, cost, description) VALUES (?, ?, ?)";
    private final String FIND_FAVOURITE_PRODUCTS_BY_USER_ID = "SELECT * FROM products p INNER JOIN favourite_products f ON p.id = f.product_id INNER JOIN users ON f.user_id=users.id WHERE user_id=?;";
    private final String FIND_PRODUCTS_IN_BUCKET_BY_USER_ID = "SELECT * FROM products p INNER JOIN bucket b ON p.id = b.product_id INNER JOIN users ON b.user_id=users.id WHERE user_id=?;";
    private final String FIND_ALL = "SELECT * FROM products;";
    private final String INSERT_PRODUCT_BUCKET = "INSERT INTO bucket(user_id, product_id) VALUES (?,?)";
    private final String INSERT_PRODUCT_FAVOURITE = "INSERT INTO favourite_products(user_id, product_id) VALUES(?, ?)";


    public ProductsRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            List<Product> products = rowMapProducts.rowMap(resultSet);
            return products;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Product save(Product product) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setDouble(2, product.getCost());
            preparedStatement.setString(3, product.getDescription());
            resultSet = preparedStatement.executeQuery();
            product = rowMapper.rowMap(resultSet);
            return product;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    private RowMapper<Product> rowMapper = ((resultSet) -> {
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
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_FAVOURITE_PRODUCTS_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            List<Product> products = rowMapProducts.rowMap(resultSet);
            return products;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public List<Product> findProductsInBucketByUserId(Long userId) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCTS_IN_BUCKET_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            List<Product> products = rowMapProducts.rowMap(resultSet);
            return products;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public void addProductToFavourite(Long userId, Long productId) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_FAVOURITE);
            completeStatement(statement, userId, productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addProductToBucket(Long userId, Long productId) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_BUCKET);
            completeStatement(statement, userId, productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        };
    }

    @Override
    public void removeFromBucket(Long userId, Long productId) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_BUCKET);
            completeStatement(statement, userId, productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        };
    }

    @Override
    public void removeFromFavourites(Long userId, Long productId) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_FAVOURITE);
            completeStatement(statement, userId, productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        };
    }

    public void completeStatement(PreparedStatement statement, Long userId, Long productId) {
        try {
            statement.setLong(1, userId);
            statement.setLong(2, productId);
            statement.execute();;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private RowMapper<List<Product>> rowMapProducts = ((resultSet) -> {
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
