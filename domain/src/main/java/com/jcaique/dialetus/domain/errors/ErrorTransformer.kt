package com.jcaique.dialetus.domain.errors

interface ErrorTransformer {
    suspend fun transform(incoming: Throwable): Throwable
}
