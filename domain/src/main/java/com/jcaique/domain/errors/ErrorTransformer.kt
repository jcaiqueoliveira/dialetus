package com.jcaique.domain.errors

interface ErrorTransformer {
    suspend fun transform(incoming: Throwable): Throwable
}
