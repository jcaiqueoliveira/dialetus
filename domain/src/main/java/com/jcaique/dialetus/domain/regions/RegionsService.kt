package com.jcaique.dialetus.domain.regions

import com.jcaique.dialetus.domain.errors.GatewayIntegrationIssues
import com.jcaique.dialetus.domain.errors.UnexpectedResponse
import com.jcaique.dialetus.domain.models.Region

interface RegionsService {

    @Throws(UnexpectedResponse::class, GatewayIntegrationIssues::class)
    suspend fun fetchRegions(): List<Region>
}
