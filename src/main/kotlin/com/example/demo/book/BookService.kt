package com.example.demo.book

import com.example.demo.book.dto.request.CreateBookInput
import com.example.demo.book.dto.request.UpdateBookInput
import com.example.demo.book.exception.BookNotFoundException
import com.example.demo.user.UserRepository
import com.example.demo.user.exception.UserNotFoundException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val bookMapper: BookMapper
) {

    @Transactional(readOnly = true)
    suspend fun getAllBooks(page: Int, pageSize: Int): Page<Book> = withContext(dispatcher) {
        val pageable: Pageable = PageRequest.of(page, pageSize)
        return@withContext bookRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    suspend fun getBookById(id: Long): Book? = withContext(dispatcher) {
        return@withContext bookRepository.findById(id).orElse(null)
    }

    suspend fun createBook(data: CreateBookInput): Book = withContext(dispatcher) {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val user = userRepository.findByEmail(userDetails.username).orElseThrow{UserNotFoundException("user not found")}
        val book = bookMapper.toBookEntity(data, user)
        return@withContext bookRepository.save(book)
    }

    suspend fun updateBook(id: Long, data: UpdateBookInput): Book? = withContext(dispatcher) {
        bookRepository.updateBookById(id, data)
        return@withContext bookRepository.findById(id).orElseThrow{BookNotFoundException("book with id: $id not found")}
    }

    suspend fun deleteBook(id: Long) = withContext(dispatcher) {
        return@withContext userRepository.deleteById(id)
    }
}
