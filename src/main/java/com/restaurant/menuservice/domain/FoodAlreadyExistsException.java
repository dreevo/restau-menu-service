package com.restaurant.menuservice.domain;

public class FoodAlreadyExistsException extends RuntimeException{

    public FoodAlreadyExistsException(String ref) {
        super("Food with ref " + ref + " already exists.");
    }
}
