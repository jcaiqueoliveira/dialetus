package com.jcaique.data.networking

import com.jcaique.domain.errors.ErrorTransformer
import com.jcaique.domain.errors.GatewayIntegrationIssues
import retrofit2.HttpException

internal object HttpErrorHandler : ErrorTransformer {
    override suspend fun transform(incoming: Throwable): Throwable = when (incoming) {
        is HttpException -> handleHttpError(incoming.code())
        else -> incoming
    }

    private fun handleHttpError(code: Int): Throwable = when (code) {
        404 -> GatewayIntegrationIssues.NotFound
        in 400..499 -> GatewayIntegrationIssues.ClientIssue
        else -> GatewayIntegrationIssues.ServerIssue
    }
}
