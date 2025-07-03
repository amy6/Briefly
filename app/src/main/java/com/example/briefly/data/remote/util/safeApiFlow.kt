package com.example.briefly.data.remote.util

import com.example.briefly.core.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

inline fun <T> safeApiFlow(
    networkUtils: NetworkUtils,
    crossinline apiCall: suspend () -> T,
): Flow<Result<T>> = flow {
    emit(Result.Loading())

    if (!networkUtils.isConnected()) {
        emit(Result.Error("No internet connection"))
        return@flow
    }

    try {
        val response = apiCall()
        emit(Result.Success(response))
    } catch (e: HttpException) {
        emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred"))
    } catch (e: IOException) {
        emit(Result.Error("Couldn't reach server. Check your internet connection."))
    } catch (e: Exception) {
        emit(Result.Error("Something went wrong: ${e.localizedMessage}"))
    }
}