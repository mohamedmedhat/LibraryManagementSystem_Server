package com.example.demo.user

import com.example.demo.book.Book
import com.example.demo.shared.enums.Role
import jakarta.persistence.*

@Entity
@Table(name = "users", indexes = [
    Index(name = "users_email_idx", columnList = "email"),
    Index(name = "users_password_idx", columnList = "password"),
    Index(name = "users_roles_idx", columnList = "roles")
])
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    val email: String,

    val password: String,

    @Enumerated(EnumType.STRING)
    val roles: Set<Role>,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY , orphanRemoval = true)
    val books: MutableList<Book> = mutableListOf()
)
