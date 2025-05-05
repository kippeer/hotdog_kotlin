package com.hotdogshop.controller

import com.hotdogshop.model.Order
import com.hotdogshop.model.OrderItem
import com.hotdogshop.model.OrderStatus
import com.hotdogshop.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.validation.Valid

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "Order management APIs")
class OrderController(private val orderService: OrderService) {

    @GetMapping
    @Operation(summary = "Get all orders")
    fun getAllOrders(): ResponseEntity<List<Order>> {
        val orders = orderService.getAllOrders()
        return ResponseEntity.ok(orders)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    fun getOrderById(@PathVariable id: Long): ResponseEntity<Order> {
        val order = orderService.getOrderById(id)
        return ResponseEntity.ok(order)
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get orders by customer ID")
    fun getOrdersByCustomer(@PathVariable customerId: Long): ResponseEntity<List<Order>> {
        val orders = orderService.getOrdersByCustomer(customerId)
        return ResponseEntity.ok(orders)
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get orders by status")
    fun getOrdersByStatus(@PathVariable status: OrderStatus): ResponseEntity<List<Order>> {
        val orders = orderService.getOrdersByStatus(status)
        return ResponseEntity.ok(orders)
    }

    @GetMapping("/pending")
    @Operation(summary = "Get pending orders")
    fun getPendingOrders(): ResponseEntity<List<Order>> {
        val pendingOrders = orderService.getPendingOrders()
        return ResponseEntity.ok(pendingOrders)
    }

    @GetMapping("/date/{date}")
    @Operation(summary = "Get orders for a specific date")
    fun getOrdersForDate(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ResponseEntity<List<Order>> {
        val orders = orderService.getOrdersForDate(date)
        return ResponseEntity.ok(orders)
    }

    @PostMapping
    @Operation(summary = "Create a new order")
    fun createOrder(@Valid @RequestBody order: Order): ResponseEntity<Order> {
        val createdOrder = orderService.createOrder(order)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder)
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update order status")
    fun updateOrderStatus(
        @PathVariable id: Long,
        @RequestParam status: OrderStatus
    ): ResponseEntity<Order> {
        val updatedOrder = orderService.updateOrderStatus(id, status)
        return ResponseEntity.ok(updatedOrder)
    }

    @PostMapping("/{orderId}/items")
    @Operation(summary = "Add item to order")
    fun addItemToOrder(
        @PathVariable orderId: Long,
        @Valid @RequestBody item: OrderItem
    ): ResponseEntity<Order> {
        val updatedOrder = orderService.addItemToOrder(orderId, item)
        return ResponseEntity.ok(updatedOrder)
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Remove item from order")
    fun removeItemFromOrder(
        @PathVariable orderId: Long,
        @PathVariable itemId: Long
    ): ResponseEntity<Order> {
        val updatedOrder = orderService.removeItemFromOrder(orderId, itemId)
        return ResponseEntity.ok(updatedOrder)
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel an order")
    fun cancelOrder(@PathVariable id: Long): ResponseEntity<Order> {
        val cancelledOrder = orderService.cancelOrder(id)
        return ResponseEntity.ok(cancelledOrder)
    }

    @GetMapping("/sales/{date}")
    @Operation(summary = "Get daily sales report")
    fun getDailySalesReport(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): ResponseEntity<Double> {
        val salesTotal = orderService.getDailySalesReport(date)
        return ResponseEntity.ok(salesTotal)
    }
}