package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Auth;

public interface AuthRepository extends CrudRepository<Auth> {
    Auth findByCookieValue(String cookieValue);
    void save(Auth auth);
}
