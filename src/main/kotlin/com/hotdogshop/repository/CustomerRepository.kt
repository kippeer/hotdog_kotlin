package com.hotdogshop.repository

import com.hotdogshop.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findByEmail(email: String): Optional<Customer>
    fun findByPhone(phone: String): Optional<Customer>
    fun findByNameContainingIgnoreCase(name: String): List<Customer>
}