package com.example.demo.resolver

import com.example.demo.dto.book.CreateBookInput
import com.example.demo.dto.book.UpdateBookInput
import com.example.demo.model.Book
import com.example.demo.service.BookService
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class BookResolver(private val bookService: BookService) {

    @QueryMapping
    fun books(): List<Book> = bookService.getAllBooks()

    @QueryMapping
    fun book(@Argument id: Long): Book? = bookService.getBookById(id)

    @MutationMapping
    fun createBook(@Valid @Argument data: CreateBookInput): Book {
        return bookService.createBook(data)
    }

    @MutationMapping
    fun updateBook(@Argument id: Long, @Argument data: UpdateBookInput): Book? {
        return bookService.updateBook(id, data)
    }

    @MutationMapping
    fun deleteBook(@Argument id: Long): Boolean = bookService.deleteBook(id)
}
