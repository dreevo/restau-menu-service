package com.restaurant.menuservice.domain;

public class FoodNotFoundException extends RuntimeException{

    public FoodNotFoundException(String ref) {
        super("The food with ref " + ref + " was not found.");
    }
}
