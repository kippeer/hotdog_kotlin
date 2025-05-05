package com.hotdogshop.service

import com.hotdogshop.exception.ResourceNotFoundException
import com.hotdogshop.model.Product
import com.hotdogshop.model.ProductCategory
import com.hotdogshop.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun getProductById(id: Long): Product = productRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Product not found with id: $id") }

    fun getProductsByName(name: String): List<Product> = 
        productRepository.findByNameContainingIgnoreCase(name)

    fun getProductsByCategory(category: ProductCategory): List<Product> = 
        productRepository.findByCategory(category)

    fun getAvailableProducts(): List<Product> = productRepository.findByAvailable(true)

    fun getAvailableProductsByCategory(category: ProductCategory): List<Product> =
        productRepository.findByCategoryAndAvailable(category, true)

    @Transactional
    fun createProduct(product: Product): Product = productRepository.save(product)

    @Transactional
    fun updateProduct(id: Long, productDetails: Product): Product {
        val product = getProductById(id)
        
        product.name = productDetails.name
        product.description = productDetails.description
        product.price = productDetails.price
        product.available = productDetails.available
        product.ingredients = productDetails.ingredients
        product.category = productDetails.category
        
        return productRepository.save(product)
    }

    @Transactional
    fun deleteProduct(id: Long) {
        val product = getProductById(id)
        productRepository.delete(product)
    }

    @Transactional
    fun toggleAvailability(id: Long): Product {
        val product = getProductById(id)
        product.available = !product.available
        return productRepository.save(product)
    }
}