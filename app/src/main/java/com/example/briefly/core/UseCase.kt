package com.example.briefly.core

interface UseCase<T, R> {
    operator fun invoke(input: T): R
}

interface NoInputSuspendingUseCase<R> {
    suspend operator fun invoke(): R
}

interface SuspendingUseCase<T, R> {
    suspend operator fun invoke(input: T): R
}