package com.hotdogshop.model

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null,
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @field:NotNull(message = "Product is required")
    var product: Product,
    
    @field:Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    var quantity: Int = 1,
    
    @Column(nullable = false)
    var unitPrice: BigDecimal,
    
    @Column(nullable = false)
    var subtotal: BigDecimal,
    
    @ElementCollection
    @CollectionTable(name = "order_item_customizations", joinColumns = [JoinColumn(name = "order_item_id")])
    @Column(name = "customization")
    var customizations: MutableSet<String> = mutableSetOf()
) {
    // Calculate subtotal whenever quantity or price changes
    fun updateSubtotal() {
        subtotal = unitPrice.multiply(BigDecimal(quantity))
    }
    
    // Initialize with calculated subtotal
    constructor(
        product: Product,
        quantity: Int = 1,
        customizations: MutableSet<String> = mutableSetOf()
    ) : this(
        id = 0,
        order = null,
        product = product,
        quantity = quantity,
        unitPrice = product.price,
        subtotal = product.price.multiply(BigDecimal(quantity)),
        customizations = customizations
    )
}