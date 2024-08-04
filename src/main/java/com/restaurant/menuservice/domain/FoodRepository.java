package com.restaurant.menuservice.domain;

import java.util.Optional;

public interface FoodRepository {

    Iterable<Food> findAll();
    Optional<Food> findByRef(String ref);
    boolean existsByRef(String ref);
    Food save(Food food);
    void deleteByRef(String ref);
}
