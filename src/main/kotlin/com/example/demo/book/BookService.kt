package com.example.demo.book

import com.example.demo.book.dto.CreateBookInput
import com.example.demo.book.dto.UpdateBookInput
import com.example.demo.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository
) {

    suspend fun getAllBooks(page: Int, pageSize: Int): Page<Book> = withContext(Dispatchers.IO) {
        val pageable: Pageable = PageRequest.of(page, pageSize)
        return@withContext bookRepository.findAll(pageable)
    }

    suspend fun getBookById(id: Long): Book? = withContext(Dispatchers.IO) {
        return@withContext bookRepository.findById(id).orElse(null)
    }

    suspend fun createBook(data: CreateBookInput): Book = withContext(Dispatchers.IO) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val user = userRepository.findByEmail(userDetails.username).orElse(null)
        val book = Book(
            name = data.name,
            author = data.author,
            publishYear = data.publishYear,
            price = data.price,
            user = user
        )
        return@withContext bookRepository.save(book)
    }

    suspend fun updateBook(id: Long, data: UpdateBookInput): Book? = withContext(Dispatchers.IO) {
        val book = bookRepository.findById(id).orElse(null) ?: return@withContext null
        val updatedBook = book.copy(
            name = data.name,
            author = data.author,
            publishYear = data.publishYear,
            price = data.price
        )
        return@withContext bookRepository.save(updatedBook)
    }

    suspend fun deleteBook(id: Long): Boolean = withContext(Dispatchers.IO) {
        return@withContext if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
