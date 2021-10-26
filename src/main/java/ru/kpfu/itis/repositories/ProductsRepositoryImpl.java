package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.models.Product;

import javax.swing.text.html.HTMLDocument;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryImpl implements ProductsRepository {

    private final Connection connection;

    //language=sql
    private final String INSERT_PRODUCT = "INSERT INTO products(title, cost, description) VALUES (?, ?, ?)";
    private final String FIND_FAVOURITE_PRODUCTS_BY_USER_ID = "SELECT * FROM products p INNER JOIN favourite_products f ON p.id = f.product_id INNER JOIN users ON f.user_id=users.id WHERE user_id=?;";
    private final String FIND_PRODUCTS_IN_BUCKET_BY_USER_ID = "SELECT * FROM products p INNER JOIN bucket b ON p.id = b.product_id INNER JOIN users ON b.user_id=users.id WHERE user_id=?;";
    private final String FIND_ALL = "SELECT * FROM products;";
    private final String ADD_PRODUCT_TO_FAVOURITES = "INSERT INTO favourite_products(user_id, product_id) VALUES (?, ?)";
    private final String ADD_PRODUCT_TO_BUCKET = "INSERT INTO bucket(user_id, product_id) VALUES (?, ?)";
    private final String FIND_FAVOURITE_BY_USER_AND_PRODUCT =
            "SELECT * FROM favourite_products WHERE user_id=? and product_id=?";
    private final String FIND_IN_BUCKET_BY_USER_AND_PRODUCT =
            "SELECT * FROM bucket WHERE user_id=? and product_id=?";
    private final String DELETE_FROM_BUCKET = "DELETE FROM bucket WHERE user_id=? and product_id=?";
    private final String DELETE_FROM_FAVOURITES = "DELETE FROM favourite_products WHERE user_id=? and product_id=?";

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            return rowMapProducts.rowMap(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addToFavourite(Long userId, Long productId) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_TO_FAVOURITES);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productId);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAlreadyInFavourite(Long userId, Long productId){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_FAVOURITE_BY_USER_AND_PRODUCT);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public boolean isAlreadyInBucket(Long userId, Long productId) {
        try{
            PreparedStatement statement = connection.prepareStatement(FIND_IN_BUCKET_BY_USER_AND_PRODUCT);
            statement.setLong(1, userId);
            statement.setLong(2, productId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public void addToBucket(Long userId, Long productId) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_TO_BUCKET);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productId);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromBucket(Long userId, Long productId) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_BUCKET);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productId);
            preparedStatement.execute();
        }catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void deleteFromFavourites(Long userId, Long productId) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_FAVOURITES);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productId);
            preparedStatement.execute();
        }catch (SQLException e){
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