package com.example.demo.book

import com.example.demo.book.dto.request.CreateBookInput
import com.example.demo.user.User
import org.springframework.stereotype.Component

@Component
class BookMapper {

    fun toBookEntity(data: CreateBookInput, user: User): Book{
        return Book(
            name = data.name,
            author = data.author,
            publishYear = data.publishYear,
            price = data.price,
            user = user
        )
    }
}