package com.example.demo.book

import com.example.demo.book.dto.request.UpdateBookInput
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
interface BookRepository : JpaRepository<Book, Long> {
    @Transactional(readOnly = false)
    @Modifying
    @Query(
        value = """
    UPDATE books
    SET name = :#{#data.name}, author = :#{#data.author}, publish_year = :#{#data.publishYear}, price = :#{#data.price}
    WHERE id = :id
""", nativeQuery = true
    )
    fun updateBookById(@Param("id") id: Long, @Param("data") data: UpdateBookInput)
}