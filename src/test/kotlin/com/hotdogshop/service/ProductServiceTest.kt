package com.hotdogshop.service

import com.hotdogshop.exception.ResourceNotFoundException
import com.hotdogshop.model.Product
import com.hotdogshop.model.ProductCategory
import com.hotdogshop.repository.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var productService: ProductService

    @Test
    fun `should return all products when getting all products`() {
        // Given
        val products = listOf(
            Product(
                id = 1L,
                name = "Classic Hot Dog",
                price = BigDecimal("5.99"),
                category = ProductCategory.REGULAR
            ),
            Product(
                id = 2L,
                name = "Veggie Hot Dog",
                price = BigDecimal("7.99"),
                category = ProductCategory.VEGGIE
            )
        )
        
        `when`(productRepository.findAll()).thenReturn(products)

        // When
        val result = productService.getAllProducts()

        // Then
        assertEquals(2, result.size)
        assertEquals("Classic Hot Dog", result[0].name)
        assertEquals("Veggie Hot Dog", result[1].name)
    }

    @Test
    fun `should return product when getting product by ID`() {
        // Given
        val product = Product(
            id = 1L,
            name = "Classic Hot Dog",
            price = BigDecimal("5.99"),
            category = ProductCategory.REGULAR
        )
        
        `when`(productRepository.findById(1L)).thenReturn(Optional.of(product))

        // When
        val result = productService.getProductById(1L)

        // Then
        assertEquals(1L, result.id)
        assertEquals("Classic Hot Dog", result.name)
        assertEquals(BigDecimal("5.99"), result.price)
    }

    @Test
    fun `should throw exception when product with ID does not exist`() {
        // Given
        `when`(productRepository.findById(99L)).thenReturn(Optional.empty())

        // When & Then
        val exception = assertThrows(ResourceNotFoundException::class.java) {
            productService.getProductById(99L)
        }
        
        assertEquals("Product not found with id: 99", exception.message)
    }

    @Test
    fun `should create a new product`() {
        // Given
        val newProduct = Product(
            name = "New Hot Dog",
            price = BigDecimal("9.99"),
            category = ProductCategory.PREMIUM
        )
        
        val savedProduct = Product(
            id = 3L,
            name = "New Hot Dog",
            price = BigDecimal("9.99"),
            category = ProductCategory.PREMIUM
        )
        
        `when`(productRepository.save(newProduct)).thenReturn(savedProduct)

        // When
        val result = productService.createProduct(newProduct)

        // Then
        assertEquals(3L, result.id)
        assertEquals("New Hot Dog", result.name)
        assertEquals(BigDecimal("9.99"), result.price)
    }

    @Test
    fun `should toggle product availability`() {
        // Given
        val product = Product(
            id = 1L,
            name = "Classic Hot Dog",
            price = BigDecimal("5.99"),
            available = true,
            category = ProductCategory.REGULAR
        )
        
        val updatedProduct = Product(
            id = 1L,
            name = "Classic Hot Dog",
            price = BigDecimal("5.99"),
            available = false,
            category = ProductCategory.REGULAR
        )
        
        `when`(productRepository.findById(1L)).thenReturn(Optional.of(product))
        `when`(productRepository.save(any(Product::class.java))).thenReturn(updatedProduct)

        // When
        val result = productService.toggleAvailability(1L)

        // Then
        assertFalse(result.available)
    }
}