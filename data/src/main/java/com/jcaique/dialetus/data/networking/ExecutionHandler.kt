package com.jcaique.dialetus.data.networking

internal suspend fun <T> executionHandler(func: suspend () -> T): T {
    val transformers = listOf(
        HttpErrorHandler,
        SerializationErrorHandler
    )
    return try {
        func.invoke()
    } catch (throwable: Throwable) {
        throw transformers
            .map { it.transform(throwable) }
            .reduce { transformed, current ->
                when {
                    transformed == current -> transformed
                    current == throwable -> transformed
                    else -> current
                }
            }
    }
}
