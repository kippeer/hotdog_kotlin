package com.hotdogshop.service

import com.hotdogshop.exception.ResourceNotFoundException
import com.hotdogshop.model.Customer
import com.hotdogshop.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomerService(private val customerRepository: CustomerRepository) {

    fun getAllCustomers(): List<Customer> = customerRepository.findAll()

    fun getCustomerById(id: Long): Customer = customerRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Customer not found with id: $id") }

    fun getCustomerByEmail(email: String): Customer = customerRepository.findByEmail(email)
        .orElseThrow { ResourceNotFoundException("Customer not found with email: $email") }

    fun findCustomersByName(name: String): List<Customer> =
        customerRepository.findByNameContainingIgnoreCase(name)

    @Transactional
    fun createCustomer(customer: Customer): Customer = customerRepository.save(customer)

    @Transactional
    fun updateCustomer(id: Long, customerDetails: Customer): Customer {
        val customer = getCustomerById(id)
        
        customer.name = customerDetails.name
        customer.email = customerDetails.email
        customer.phone = customerDetails.phone
        customer.address = customerDetails.address
        
        return customerRepository.save(customer)
    }

    @Transactional
    fun deleteCustomer(id: Long) {
        val customer = getCustomerById(id)
        customerRepository.delete(customer)
    }
}