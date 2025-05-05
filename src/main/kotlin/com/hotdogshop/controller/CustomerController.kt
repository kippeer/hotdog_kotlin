package com.hotdogshop.controller

import com.hotdogshop.model.Customer
import com.hotdogshop.service.CustomerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Customer management APIs")
class CustomerController(private val customerService: CustomerService) {

    @GetMapping
    @Operation(summary = "Get all customers")
    fun getAllCustomers(): ResponseEntity<List<Customer>> {
        val customers = customerService.getAllCustomers()
        return ResponseEntity.ok(customers)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    fun getCustomerById(@PathVariable id: Long): ResponseEntity<Customer> {
        val customer = customerService.getCustomerById(id)
        return ResponseEntity.ok(customer)
    }

    @GetMapping("/search")
    @Operation(summary = "Search customers by name")
    fun searchCustomers(@RequestParam name: String): ResponseEntity<List<Customer>> {
        val customers = customerService.findCustomersByName(name)
        return ResponseEntity.ok(customers)
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get customer by email")
    fun getCustomerByEmail(@PathVariable email: String): ResponseEntity<Customer> {
        val customer = customerService.getCustomerByEmail(email)
        return ResponseEntity.ok(customer)
    }

    @PostMapping
    @Operation(summary = "Create a new customer")
    fun createCustomer(@Valid @RequestBody customer: Customer): ResponseEntity<Customer> {
        val createdCustomer = customerService.createCustomer(customer)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing customer")
    fun updateCustomer(
        @PathVariable id: Long,
        @Valid @RequestBody customerDetails: Customer
    ): ResponseEntity<Customer> {
        val updatedCustomer = customerService.updateCustomer(id, customerDetails)
        return ResponseEntity.ok(updatedCustomer)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer")
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<Void> {
        customerService.deleteCustomer(id)
        return ResponseEntity.noContent().build()
    }
}