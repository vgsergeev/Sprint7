package ru.praktikum.pojos;

import io.qameta.allure.Step;

public class OrderPojo {

    private String firstName;
    private String lastName;
    private String address;
    private Integer metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
    private Integer track;


    public String getFirstName() {
        return firstName;
    }

    @Step("Set customer first name")
    public OrderPojo withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    @Step("Set customer last name")
    public OrderPojo withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    @Step("Set customer address")
    public OrderPojo withAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getMetroStation() {
        return metroStation;
    }

    @Step("Set customer nearest metro station")
    public OrderPojo withMetroStation(Integer metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    @Step("Set rent time duration")
    public OrderPojo withRentTime(Integer rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    @Step("Set customer phone")
    public OrderPojo withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    @Step("Set delivery date")
    public OrderPojo withDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String getComment() {
        return comment;
    }

    @Step("Set order comment")
    public OrderPojo withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String[] getColor() {
        return color;
    }

    @Step ("Set color for order")
    public OrderPojo withColor(String[] color) {
        this.color = color;
        return this;
    }

    @Step("Get order track")
    public Integer getTrack() {
        return track;
    }

    @Step("Set order track")
    public OrderPojo withTrack(Integer track) {
        this.track = track;
        return this;
    }
}