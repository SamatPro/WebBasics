package ru.kpfu.itis.models;

import java.util.List;

public class User {
    private Long id;
    private String login;
    private String passwordHash;
    private String lastName;
    private String firstName;

    private List<Product> bucket;

    private List<Product> favourites;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Product> getBucket() {
        return bucket;
    }

    public void setBucket(List<Product> bucket) {
        this.bucket = bucket;
    }

    public List<Product> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Product> favourites) {
        this.favourites = favourites;
    }
}
