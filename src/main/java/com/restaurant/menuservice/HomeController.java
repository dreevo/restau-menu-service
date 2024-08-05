package com.restaurant.menuservice;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final RestaurantProperties restaurantProperties;
    public HomeController(RestaurantProperties restaurantProperties) {
        this.restaurantProperties = restaurantProperties;
    }

    @GetMapping("/")
    public String home() {
        return restaurantProperties.getGreeting();
    }
}
