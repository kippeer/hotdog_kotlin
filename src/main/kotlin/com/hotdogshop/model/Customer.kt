package com.hotdogshop.model

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Entity
@Table(name = "customers")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @field:NotBlank(message = "Name is required")
    @Column(nullable = false)
    var name: String,
    
    @field:Email(message = "Email should be valid")
    @Column(unique = true)
    var email: String? = null,
    
    @field:Pattern(regexp = "\\d{10,11}", message = "Phone must be 10-11 digits")
    @Column
    var phone: String? = null,
    
    @Column
    var address: String? = null,
    
    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var orders: MutableList<Order> = mutableListOf()
)