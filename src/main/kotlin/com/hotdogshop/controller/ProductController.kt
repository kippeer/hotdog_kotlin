package com.hotdogshop.controller

import com.hotdogshop.model.Product
import com.hotdogshop.model.ProductCategory
import com.hotdogshop.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management APIs")
class ProductController(private val productService: ProductService) {

    @GetMapping
    @Operation(summary = "Get all products")
    fun getAllProducts(
        @RequestParam(required = false) category: ProductCategory?,
        @RequestParam(required = false) available: Boolean?
    ): ResponseEntity<List<Product>> {
        val products = when {
            category != null && available != null -> 
                productService.getAvailableProductsByCategory(category)
            category != null -> 
                productService.getProductsByCategory(category)
            available != null && available -> 
                productService.getAvailableProducts()
            else -> 
                productService.getAllProducts()
        }
        return ResponseEntity.ok(products)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productService.getProductById(id)
        return ResponseEntity.ok(product)
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name")
    fun searchProducts(@RequestParam name: String): ResponseEntity<List<Product>> {
        val products = productService.getProductsByName(name)
        return ResponseEntity.ok(products)
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    fun createProduct(@Valid @RequestBody product: Product): ResponseEntity<Product> {
        val createdProduct = productService.createProduct(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    fun updateProduct(
        @PathVariable id: Long,
        @Valid @RequestBody productDetails: Product
    ): ResponseEntity<Product> {
        val updatedProduct = productService.updateProduct(id, productDetails)
        return ResponseEntity.ok(updatedProduct)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}/availability")
    @Operation(summary = "Toggle product availability")
    fun toggleProductAvailability(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productService.toggleAvailability(id)
        return ResponseEntity.ok(product)
    }
}