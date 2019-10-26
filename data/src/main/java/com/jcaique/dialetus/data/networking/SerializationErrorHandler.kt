package com.jcaique.dialetus.data.networking

import com.jcaique.dialetus.domain.errors.ErrorTransformer
import com.jcaique.dialetus.domain.errors.UnexpectedResponse
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
