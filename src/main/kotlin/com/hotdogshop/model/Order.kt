package com.hotdogshop.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null,
    
    @Column(nullable = false)
    var orderDate: LocalDateTime = LocalDateTime.now(),
    
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: MutableList<OrderItem> = mutableListOf(),
    
    @Column(nullable = false)
    var totalAmount: BigDecimal = BigDecimal.ZERO,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: OrderStatus = OrderStatus.PENDING,
    
    @Column
    var notes: String? = null,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var paymentMethod: PaymentMethod = PaymentMethod.CASH
) {
    // Helper method to add items and update total
    fun addItem(item: OrderItem) {
        items.add(item)
        item.order = this
        totalAmount = totalAmount.add(item.subtotal)
    }
    
    // Helper method to remove items and update total
    fun removeItem(item: OrderItem) {
        items.remove(item)
        totalAmount = totalAmount.subtract(item.subtotal)
    }
}

enum class OrderStatus {
    PENDING, PREPARING, READY, DELIVERED, CANCELLED
}

enum class PaymentMethod {
    CASH, CREDIT_CARD, DEBIT_CARD, TRANSFER, ONLINE
}