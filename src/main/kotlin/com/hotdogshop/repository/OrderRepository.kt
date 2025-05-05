package com.hotdogshop.repository

import com.hotdogshop.model.Order
import com.hotdogshop.model.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findByCustomerId(customerId: Long): List<Order>
    fun findByStatus(status: OrderStatus): List<Order>
    fun findByOrderDateBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Order>
    
    @Query("SELECT o FROM Order o WHERE o.status = ?1 ORDER BY o.orderDate ASC")
    fun findOrdersByStatusOrderByDateAsc(status: OrderStatus): List<Order>
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderDate BETWEEN ?1 AND ?2")
    fun getTotalSalesForPeriod(startDate: LocalDateTime, endDate: LocalDateTime): Double
}