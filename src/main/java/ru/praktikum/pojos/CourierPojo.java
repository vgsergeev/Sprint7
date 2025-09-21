package ru.praktikum.pojos;

import io.qameta.allure.Step;

public class CourierPojo {
    private String login;
    private String password;
    private String firstName;
    private Integer id;

    public String getLogin() {
        return login;
    }

    @Step("Set courier login")
    public CourierPojo withLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    @Step ("Set courier password")
    public CourierPojo withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    @Step ("Set courier first name")
    public CourierPojo withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Step ("Get courier id")
    public Integer getId() {
        return id;
    }

    @Step ("Set courier id")
    public CourierPojo withId(Integer id) {
        this.id = id;
        return this;
    }
}