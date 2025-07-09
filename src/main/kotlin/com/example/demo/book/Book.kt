package com.example.demo.book

import com.example.demo.user.User
import jakarta.persistence.*

@Entity
data class Book(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val name: String,

    @Column(unique = true)
    val author: String,

    @Column(unique = true)
    val publishYear: Int,

    @Column(unique = true)
    val price: Float,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = null
)
