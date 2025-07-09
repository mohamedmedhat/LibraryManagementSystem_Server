package com.example.demo.book

import com.example.demo.book.dto.request.CreateBookInput
import com.example.demo.book.dto.request.UpdateBookInput
import jakarta.validation.Valid
import kotlinx.coroutines.runBlocking
import org.springframework.data.domain.Page
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class BookResolver(private val bookService: BookService) {

    @QueryMapping
    fun books(
        @Argument page: Int = 0,
        @Argument pageSize: Int = 10
    ): Page<Book> = runBlocking {
        return@runBlocking bookService.getAllBooks(page, pageSize)
    }

    @QueryMapping
    fun book(@Argument id: Long): Book? = runBlocking {
        return@runBlocking bookService.getBookById(id)
    }

    @MutationMapping
    fun createBook(@Valid @Argument data: CreateBookInput): Book = runBlocking {
        return@runBlocking bookService.createBook(data)
    }

    @MutationMapping
    fun updateBook(@Argument id: Long, @Argument data: UpdateBookInput): Book? = runBlocking {
        return@runBlocking bookService.updateBook(id, data)
    }

    @MutationMapping
    fun deleteBook(@Argument id: Long): Boolean = runBlocking {
        return@runBlocking bookService.deleteBook(id)
    }
}
