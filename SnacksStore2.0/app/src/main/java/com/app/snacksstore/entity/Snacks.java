package com.app.snacksstore.entity;


public class Snacks {
    private int id;
    private String name;
    private double weight;
    private double heat;
    private double price;
    private String expireDate;

    public Snacks(int id, String name, double weight, double heat, double price, String expireDate) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.heat = heat;
        this.price = price;
        this.expireDate = expireDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

}
