package com.example.briefly.data.remote.util

import com.example.briefly.domain.network.NetworkUtils
import com.example.briefly.core.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import okio.IOException
import retrofit2.HttpException

// A generic helper to safely execute suspend API calls
// Wraps the result in a Result<T>, catching exceptions like IO/network failures
// Ensures ViewModel/UI can handle states like success, error, or loading uniformly

suspend inline fun <T> safeApiFlow(
    networkUtils: NetworkUtils,
    timeoutMillis: Long = 15_000,
    crossinline apiCall: suspend () -> T,
): Result<T> {
    if (!networkUtils.isConnected()) {
        // Offline scenarios are handled directly in the repository to centralize network error control
        // and maintain clean separation from UI logic. A short delay is added before emitting an error
        // to ensure consistent refresh/loading animations and avoid abrupt state changes in Compose.
        delay(300)
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