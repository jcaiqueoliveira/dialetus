package com.jcaique.dialetus.domain.errors

sealed class GatewayIntegrationIssues : Throwable() {
    object NotFound : GatewayIntegrationIssues()
    object ClientIssue : GatewayIntegrationIssues()
    object ServerIssue : GatewayIntegrationIssues()
}
