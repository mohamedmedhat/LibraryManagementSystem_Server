package com.example.demo.service

import com.example.demo.dto.book.CreateBookInput
import com.example.demo.dto.book.UpdateBookInput
import com.example.demo.model.Book
import com.example.demo.repository.BookRepository
import com.example.demo.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository
) {

    fun getAllBooks(): List<Book> = bookRepository.findAll()

    fun getBookById(id: Long): Book? = bookRepository.findById(id).orElse(null)

    fun createBook(data: CreateBookInput): Book {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val user = userRepository.findByEmail(userDetails.username).orElse(null)
        val book = Book(
            name = data.name,
            author = data.author,
            publishYear = data.publishYear,
            price = data.price,
            user = user
        )
        return bookRepository.save(book)
    }

    fun updateBook(id: Long, data: UpdateBookInput): Book? {
        val book = bookRepository.findById(id).orElse(null) ?: return null
        val updatedBook = book.copy(
            name = data.name,
            author = data.author,
            publishYear = data.publishYear,
            price = data.price
        )
        return bookRepository.save(updatedBook)
    }

    fun deleteBook(id: Long): Boolean {
        return if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
