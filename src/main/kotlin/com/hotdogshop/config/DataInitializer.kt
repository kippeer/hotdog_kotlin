package com.hotdogshop.config

import com.hotdogshop.model.Product
import com.hotdogshop.model.ProductCategory
import com.hotdogshop.repository.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
class DataInitializer {

    @Bean
    fun loadData(productRepository: ProductRepository): CommandLineRunner {
        return CommandLineRunner {
            // Check if we need to initialize data
            if (productRepository.count() == 0L) {
                println("Initializing sample data...")
                
                // Sample hot dogs
                val classicHotDog = Product(
                    name = "Classic Hot Dog",
                    description = "Traditional hot dog with ketchup, mustard, and relish",
                    price = BigDecimal("5.99"),
                    ingredients = mutableSetOf("Beef sausage", "Bun", "Ketchup", "Mustard", "Relish"),
                    category = ProductCategory.REGULAR
                )
                
                val cheeseHotDog = Product(
                    name = "Cheese Hot Dog",
                    description = "Hot dog with melted cheddar cheese",
                    price = BigDecimal("6.99"),
                    ingredients = mutableSetOf("Beef sausage", "Bun", "Cheddar cheese", "Ketchup", "Mustard"),
                    category = ProductCategory.REGULAR
                )
                
                val chicagoStyle = Product(
                    name = "Chicago Style",
                    description = "Hot dog with tomato, pickle, relish, onions, sport peppers, and celery salt",
                    price = BigDecimal("8.99"),
                    ingredients = mutableSetOf(
                        "Beef sausage", "Poppy seed bun", "Tomato", "Pickle", "Relish", 
                        "Onions", "Sport peppers", "Celery salt"
                    ),
                    category = ProductCategory.PREMIUM
                )
                
                val veggieHotDog = Product(
                    name = "Veggie Hot Dog",
                    description = "Plant-based hot dog with your choice of toppings",
                    price = BigDecimal("7.99"),
                    ingredients = mutableSetOf("Plant-based sausage", "Bun", "Ketchup", "Mustard", "Relish"),
                    category = ProductCategory.VEGGIE
                )
                
                val hotDogCombo = Product(
                    name = "Hot Dog Combo",
                    description = "Classic hot dog with fries and a drink",
                    price = BigDecimal("9.99"),
                    ingredients = mutableSetOf(
                        "Beef sausage", "Bun", "Ketchup", "Mustard", "Relish", 
                        "French fries", "Soft drink"
                    ),
                    category = ProductCategory.COMBO
                )
                
                val softDrink = Product(
                    name = "Soft Drink",
                    description = "Regular or diet soda",
                    price = BigDecimal("2.99"),
                    ingredients = mutableSetOf("Soda"),
                    category = ProductCategory.DRINK
                )
                
                val frenchFries = Product(
                    name = "French Fries",
                    description = "Crispy golden fries",
                    price = BigDecimal("3.99"),
                    ingredients = mutableSetOf("Potatoes", "Salt"),
                    category = ProductCategory.SIDE
                )
                
                // Save all products
                productRepository.saveAll(listOf(
                    classicHotDog, cheeseHotDog, chicagoStyle, veggieHotDog, 
                    hotDogCombo, softDrink, frenchFries
                ))
                
                println("Sample data initialized successfully!")
            }
        }
    }
}