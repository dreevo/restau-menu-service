package com.restaurant.menuservice.domain;

public class FoodAlreadyExistsException extends RuntimeException{

    public FoodAlreadyExistsException(String ref) {
        super("A food with ref " + ref + " already exists.");
    }
}
