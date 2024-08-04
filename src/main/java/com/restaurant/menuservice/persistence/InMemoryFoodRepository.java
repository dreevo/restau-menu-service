package com.restaurant.menuservice.persistence;

import com.restaurant.menuservice.domain.Food;
import com.restaurant.menuservice.domain.FoodRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryFoodRepository implements FoodRepository {

    private static final Map<String, Food> foods = new HashMap<>();

    @Override
    public Iterable<Food> findAll() {
        return foods.values();
    }

    @Override
    public Optional<Food> findByRef(String ref) {
        return existsByRef(ref) ? Optional.of(foods.get(ref)) :
                Optional.empty();
    }

    @Override
    public boolean existsByRef(String ref) {
        return foods.get(ref) != null;
    }

    @Override
    public Food save(Food food) {
        foods.put(food.ref(), food);
        return food;
    }

    @Override
    public void deleteByRef(String ref) {
        foods.remove(ref);
    }
}
