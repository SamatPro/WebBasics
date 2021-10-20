package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AuthRepostoryImpl implements AuthRepository {

    private Connection connection;

    //language=sql
    private final String SQL_FIND_BY_COOKIE_VALUE = "SELECT *, auth.id as auth_id, users.id as user_id FROM auth INNER JOIN users ON auth.user_id=users.id WHERE auth.cookie_value=?";

    //language=sql
    private final String SQL_INSERT_AUTH = "INSERT INTO auth (user_id, cookie_value) VALUES (?, ?)";


    public AuthRepostoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Auth findByCookieValue(String cookieValue) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_COOKIE_VALUE);
            preparedStatement.setString(1, cookieValue);
            resultSet = preparedStatement.executeQuery();
            Auth auth = authRowMapper.rowMap(resultSet);
            return auth;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Auth> findAll() {
        return null;
    }

    @Override
    public Optional<Auth> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Auth auth) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_AUTH, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, auth.getUser().getId());
            preparedStatement.setString(2, auth.getCookieValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

        }

    }

    @Override
    public void deleteById(Long id) {

    }

    private RowMapper<Auth> authRowMapper = (resultSet) -> {
        if (resultSet.next()) {
            Auth auth = new Auth();
            auth.setId(resultSet.getLong("auth_id"));
            auth.setCookieValue(resultSet.getString("cookie_value"));

            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setLogin(resultSet.getString("login"));
            user.setPasswordHash(resultSet.getString("password_hash"));

            auth.setUser(user);
            return auth;
        } else {
            return null;
        }
    };
}
