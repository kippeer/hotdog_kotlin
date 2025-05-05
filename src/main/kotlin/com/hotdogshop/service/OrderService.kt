package com.hotdogshop.service

import com.hotdogshop.exception.ResourceNotFoundException
import com.hotdogshop.model.*
import com.hotdogshop.repository.OrderRepository
import com.hotdogshop.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerService: CustomerService,
    private val productRepository: ProductRepository
) {

    fun getAllOrders(): List<Order> = orderRepository.findAll()

    fun getOrderById(id: Long): Order = orderRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Order not found with id: $id") }

    fun getOrdersByCustomer(customerId: Long): List<Order> {
        // Verify customer exists
        customerService.getCustomerById(customerId)
        return orderRepository.findByCustomerId(customerId)
    }

    fun getOrdersByStatus(status: OrderStatus): List<Order> =
        orderRepository.findByStatus(status)

    fun getPendingOrders(): List<Order> =
        orderRepository.findOrdersByStatusOrderByDateAsc(OrderStatus.PENDING)

    fun getOrdersForDate(date: LocalDate): List<Order> {
        val startOfDay = LocalDateTime.of(date, LocalTime.MIN)
        val endOfDay = LocalDateTime.of(date, LocalTime.MAX)
        return orderRepository.findByOrderDateBetween(startOfDay, endOfDay)
    }

    @Transactional
    fun createOrder(order: Order): Order {
        // Verify products exist and are available
        order.items.forEach { item ->
            val product = productRepository.findById(item.product.id)
                .orElseThrow { ResourceNotFoundException("Product not found with id: ${item.product.id}") }
            
            if (!product.available) {
                throw IllegalStateException("Product '${product.name}' is not available")
            }
            
            // Set the actual product reference
            item.product = product
            item.unitPrice = product.price
            item.updateSubtotal()
        }
        
        // Set order date and initial status
        order.orderDate = LocalDateTime.now()
        order.status = OrderStatus.PENDING
        
        // Calculate total amount
        order.totalAmount = order.items.sumOf { it.subtotal }
        
        return orderRepository.save(order)
    }

    @Transactional
    fun updateOrderStatus(id: Long, status: OrderStatus): Order {
        val order = getOrderById(id)
        order.status = status
        return orderRepository.save(order)
    }

    @Transactional
    fun addItemToOrder(orderId: Long, item: OrderItem): Order {
        val order = getOrderById(orderId)
        
        if (order.status != OrderStatus.PENDING) {
            throw IllegalStateException("Cannot modify order that is not in PENDING status")
        }
        
        // Verify product exists and is available
        val product = productRepository.findById(item.product.id)
            .orElseThrow { ResourceNotFoundException("Product not found with id: ${item.product.id}") }
        
        if (!product.available) {
            throw IllegalStateException("Product '${product.name}' is not available")
        }
        
        // Set the actual product reference and price
        item.product = product
        item.unitPrice = product.price
        item.updateSubtotal()
        
        // Add item to order
        order.addItem(item)
        
        return orderRepository.save(order)
    }

    @Transactional
    fun removeItemFromOrder(orderId: Long, itemId: Long): Order {
        val order = getOrderById(orderId)
        
        if (order.status != OrderStatus.PENDING) {
            throw IllegalStateException("Cannot modify order that is not in PENDING status")
        }
        
        val item = order.items.find { it.id == itemId }
            ?: throw ResourceNotFoundException("Order item not found with id: $itemId")
        
        order.removeItem(item)
        
        return orderRepository.save(order)
    }

    @Transactional
    fun cancelOrder(id: Long): Order {
        val order = getOrderById(id)
        order.status = OrderStatus.CANCELLED
        return orderRepository.save(order)
    }

    fun getDailySalesReport(date: LocalDate): Double {
        val startOfDay = LocalDateTime.of(date, LocalTime.MIN)
        val endOfDay = LocalDateTime.of(date, LocalTime.MAX)
        return orderRepository.getTotalSalesForPeriod(startOfDay, endOfDay)
    }
}