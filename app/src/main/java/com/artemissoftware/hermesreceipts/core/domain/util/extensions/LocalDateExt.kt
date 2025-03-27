package com.artemissoftware.hermesreceipts.core.domain.util.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun String.toLocalDate(): LocalDate {
    val formats = listOf(
        "dd/MM/yyyy",  // 09/06/2023
        "MM/dd/yyyy",  // 06/09/2023
        "yyyy-MM-dd",  // 2023-06-09
        "dd-MM-yyyy",  // 09-06-2023
        "MMM dd, yyyy" // Jun 09, 2023
    )

    for (format in formats) {
        try {
            val formatter = DateTimeFormatter.ofPattern(format)
            return LocalDate.parse(this, formatter)
        } catch (ex: DateTimeParseException) {
        }
    }

    return LocalDate.now()
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
}

fun LocalDate.toEpochMillis(): Long {
    return this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}