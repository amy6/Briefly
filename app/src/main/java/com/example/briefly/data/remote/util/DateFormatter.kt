package com.example.briefly.data.remote.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatter {

    fun String.formatDate(): String {
        var formattedDate = ""
        val inputSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputSdf = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
        try {
            val date = inputSdf.parse(this)
            date?.let {
                formattedDate = outputSdf.format(it)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }
}