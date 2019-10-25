package com.jcaique.data.networking

import com.jcaique.domain.errors.ErrorTransformer
import com.jcaique.domain.errors.GatewayIntegrationIssues
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import retrofit2.HttpException

internal object HttpErrorHandler : ErrorTransformer {

    private const val HTTP_CLIENT_CLOSED_REQUEST = 499

    override suspend fun transform(incoming: Throwable): Throwable = when (incoming) {
        is HttpException -> handleHttpError(incoming.code())
        else -> incoming
    }

    private fun handleHttpError(code: Int): Throwable = when (code) {
        HTTP_NOT_FOUND -> GatewayIntegrationIssues.NotFound
        in HTTP_BAD_REQUEST..HTTP_CLIENT_CLOSED_REQUEST -> GatewayIntegrationIssues.ClientIssue
        else -> GatewayIntegrationIssues.ServerIssue
    }
}
