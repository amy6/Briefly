package com.example.briefly.data.remote.util

import com.example.briefly.core.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import okio.IOException
import retrofit2.HttpException

suspend inline fun <T> safeApiFlow(
    networkUtils: NetworkUtils,
    timeoutMillis: Long = 15_000,
    crossinline apiCall: suspend () -> T,
): Result<T> {
    if (!networkUtils.isConnected()) {
        return Result.Error("No internet connection")
    }

    return try {
        val response = withTimeout(timeoutMillis) { apiCall() }
        Result.Success(response)
    } catch (e: TimeoutCancellationException) {
        Result.Error("Request timed out. Please try again.")
    } catch (e: HttpException) {
        Result.Error(e.localizedMessage ?: "An unexpected error occurred")
    } catch (e: IOException) {
        Result.Error("Couldn't reach server. Check your internet connection.")
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Result.Error("Something went wrong: ${e.localizedMessage}")
    }
}