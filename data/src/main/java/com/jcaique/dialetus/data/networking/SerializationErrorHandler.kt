package com.jcaique.dialetus.data.networking

import com.jcaique.dialetus.domain.errors.ErrorTransformer
import com.jcaique.dialetus.domain.errors.UnexpectedResponse
import kotlinx.serialization.SerializationException

internal object SerializationErrorHandler : ErrorTransformer {
    override suspend fun transform(incoming: Throwable): Throwable = when (incoming) {
        is SerializationException -> UnexpectedResponse
        else -> incoming
    }
}
