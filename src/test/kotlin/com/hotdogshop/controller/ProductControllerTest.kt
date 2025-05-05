package com.hotdogshop.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hotdogshop.model.Product
import com.hotdogshop.model.ProductCategory
import com.hotdogshop.service.ProductService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal

@WebMvcTest(ProductController::class)
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return all products when GET all products is called`() {
        // Given
        val products = listOf(
            Product(
                id = 1L,
                name = "Classic Hot Dog",
                description = "Traditional hot dog",
                price = BigDecimal("5.99"),
                category = ProductCategory.REGULAR
            ),
            Product(
                id = 2L,
                name = "Veggie Hot Dog",
                description = "Plant-based hot dog",
                price = BigDecimal("7.99"),
                category = ProductCategory.VEGGIE
            )
        )
        
        `when`(productService.getAllProducts()).thenReturn(products)

        // When & Then
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Classic Hot Dog"))
            .andExpect(jsonPath("$[0].price").value(5.99))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Veggie Hot Dog"))
            .andExpect(jsonPath("$[1].price").value(7.99))
    }

    @Test
    fun `should return a specific product when GET product by ID is called`() {
        // Given
        val product = Product(
            id = 1L,
            name = "Classic Hot Dog",
            description = "Traditional hot dog",
            price = BigDecimal("5.99"),
            category = ProductCategory.REGULAR
        )
        
        `when`(productService.getProductById(1L)).thenReturn(product)

        // When & Then
        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Classic Hot Dog"))
            .andExpect(jsonPath("$.price").value(5.99))
    }

    @Test
    fun `should create a new product when POST is called`() {
        // Given
        val newProduct = Product(
            name = "New Hot Dog",
            description = "A brand new hot dog",
            price = BigDecimal("9.99"),
            category = ProductCategory.PREMIUM
        )
        
        val savedProduct = Product(
            id = 3L,
            name = "New Hot Dog",
            description = "A brand new hot dog",
            price = BigDecimal("9.99"),
            category = ProductCategory.PREMIUM
        )
        
        `when`(productService.createProduct(newProduct)).thenReturn(savedProduct)

        // When & Then
        mockMvc.perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.name").value("New Hot Dog"))
            .andExpect(jsonPath("$.price").value(9.99))
    }
}