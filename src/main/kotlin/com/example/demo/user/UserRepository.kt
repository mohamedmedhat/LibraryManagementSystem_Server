package com.example.demo.user

import com.example.demo.user.dto.projection.UserDetailsProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    fun findByEmail(@Param("email") email: String): Optional<User>

    fun existsByEmail(email: String): Boolean

    @Query("SELECT u.email as email, u.password as password, u.roles as roles FROM User u WHERE u.email = :email")
    fun findByEmailProjection(@Param("email") email: String): Optional<UserDetailsProjection>

}



