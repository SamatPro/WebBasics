package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Bucket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BucketRepositoryImpl implements BucketRepository {
    private Connection connection;

    public BucketRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    //language=sql
    private final String SAVE_BUCKET_SQL = "INSERT INTO bucket(user_id, product_id) VALUES (?, ?)";

    @Override
    public List<Bucket> findAll() {
        return null;
    }

    @Override
    public Optional<Bucket> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Bucket save(Bucket bucket) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SAVE_BUCKET_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preparedStatement == null) {
            return null;
        }
        try {
            preparedStatement.setLong(1, bucket.getUserId());
            preparedStatement.setLong(2, bucket.getProductId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bucket;
    }

    @Override
    public void deleteById(Long id) {

    }
}
