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
        if (foodRepository.existsByRef(food.ref())) {
            throw new FoodAlreadyExistsException(food.ref());
        }
        return foodRepository.save(food);
    }

    public void removeFoodFromMenu(String ref) {
        foodRepository.deleteByRef(ref);
    }

    public Food editFoodDetails(Food food, String ref) {
        return foodRepository.findByRef(ref).map(foundFood -> {
                    var foodToUpdate = new Food(foundFood.id(), foundFood.ref(), food.description(),
                            food.price(), food.chef(), foundFood.version(), foundFood.createdDate(), foundFood.lastModifiedDate());
                    return foodRepository.save(foodToUpdate);
                }
        ).orElseThrow(() -> new FoodNotFoundException(ref));
    }
}
