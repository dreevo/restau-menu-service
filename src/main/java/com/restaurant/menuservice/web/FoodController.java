package com.restaurant.menuservice.web;

import com.restaurant.menuservice.domain.Food;
import com.restaurant.menuservice.domain.FoodService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {

        this.foodService = foodService;
    }


    @GetMapping
    public Iterable<Food> get() {
        return foodService.viewFoodsList();
    }

    @GetMapping("{ref}")
    public Food getByRef(@PathVariable String ref) {
        return foodService.viewFoodDetails(ref);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Food post(@RequestBody @Valid Food food) {
        return foodService.addFoodToMenu(food);
    }

    @DeleteMapping("{ref}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String ref) {
        foodService.removeFoodFromMenu(ref);
    }

    @PutMapping("{ref}")
    public Food put(@PathVariable @Valid String ref, @RequestBody Food food) {
        return foodService.editFoodDetails(food, ref);
    }
}
