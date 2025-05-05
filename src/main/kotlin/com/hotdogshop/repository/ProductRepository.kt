package com.hotdogshop.repository

import com.hotdogshop.model.Product
import com.hotdogshop.model.ProductCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<Product>
    fun findByCategory(category: ProductCategory): List<Product>
    fun findByAvailable(available: Boolean): List<Product>
    fun findByCategoryAndAvailable(category: ProductCategory, available: Boolean): List<Product>
}