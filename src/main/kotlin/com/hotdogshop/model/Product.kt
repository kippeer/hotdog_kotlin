package com.hotdogshop.model

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @field:NotBlank(message = "Name is required")
    @Column(nullable = false)
    var name: String,
    
    @Column(length = 1000)
    var description: String? = null,
    
    @field:Positive(message = "Price must be positive")
    @Column(nullable = false)
    var price: BigDecimal,
    
    @Column(nullable = false)
    var available: Boolean = true,
    
    @ElementCollection
    @CollectionTable(name = "product_ingredients", joinColumns = [JoinColumn(name = "product_id")])
    @Column(name = "ingredient")
    var ingredients: MutableSet<String> = mutableSetOf(),
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var category: ProductCategory = ProductCategory.REGULAR
)

enum class ProductCategory {
    REGULAR, PREMIUM, VEGGIE, COMBO, DRINK, SIDE
}