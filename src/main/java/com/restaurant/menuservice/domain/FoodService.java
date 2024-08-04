package com.restaurant.menuservice.domain;

import org.springframework.stereotype.Service;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Iterable<Food> viewFoodsList() {
        return foodRepository.findAll();
    }
    public Food viewFoodDetails(String ref) {
        return foodRepository.findByRef(ref).orElseThrow(() -> new FoodNotFoundException(ref));
    }
    public Food addFoodToMenu(Food food) {
        if (foodRepository.existsByRef(food.ref())) { throw new FoodAlreadyExistsException(food.ref());
        }
        return foodRepository.save(food);
    }
    public void removeFoodFromMenu(String ref) {
        foodRepository.deleteByRef(ref);
    }
    public Food editFoodDetails(String ref, Food food) {
        return foodRepository.findByRef(ref)
                .map(existingFood -> {
                    var foodToUpdate = new Food( existingFood.ref(),
                            existingFood.description(),
                            existingFood.price(),
                            existingFood.chef());
                    return foodRepository.save(foodToUpdate);
                })
                .orElseGet(() -> addFoodToMenu(food)); }
}
