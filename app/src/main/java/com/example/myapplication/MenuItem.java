package com.example.myapplication;

public class MenuItem {
    private String name;
    private String ingredients;
    private String price;

    public MenuItem(String name, String ingredients, String price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getPrice() {
        return price;
    }
}
