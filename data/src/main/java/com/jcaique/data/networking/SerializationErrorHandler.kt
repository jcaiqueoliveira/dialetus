package com.jcaique.data.networking

import com.jcaique.domain.errors.ErrorTransformer
import com.jcaique.domain.errors.UnexpectedResponse
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UnknownFieldException

internal object SerializationErrorHandler : ErrorTransformer {
    override suspend fun transform(incoming: Throwable): Throwable = when (incoming) {
        is MissingFieldException,
        is UnknownFieldException,
        is SerializationException -> UnexpectedResponse
        else -> incoming
    }
}
