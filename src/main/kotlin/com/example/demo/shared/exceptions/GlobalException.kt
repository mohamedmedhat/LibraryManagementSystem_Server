package com.example.demo.shared.exceptions

import com.example.demo.user.exception.BadCredentialsException
import com.example.demo.user.exception.UserAlreadyExistException
import com.example.demo.user.exception.UserNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request.requestURI)
    }

    @ExceptionHandler(UserAlreadyExistException::class)
    fun handleUserAlreadyExists(
        ex: UserAlreadyExistException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request.requestURI)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(ex: BadCredentialsException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, request.requestURI)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpected(ex: RuntimeException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request.requestURI)
    }


    private fun buildErrorResponse(
        ex: RuntimeException,
        status: HttpStatus,
        path: String
    ): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            status = status.value(),
            message = ex.message ?: "Unexpected error",
            path = path
        )
        return ResponseEntity(error, status)
    }
}
