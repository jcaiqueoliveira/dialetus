package com.jcaique.dialetus.data.service.regions

import com.jcaique.dialetus.data.mappers.RegionsMapper
import com.jcaique.dialetus.data.networking.executionHandler
import com.jcaique.dialetus.data.service.DialetusGateway
import com.jcaique.dialetus.domain.models.Region
import com.jcaique.dialetus.domain.regions.RegionsService

internal class RegionsInfrastructure(private val api: DialetusGateway) :
    RegionsService {

    override suspend fun fetchRegions(): List<Region> =
        executionHandler {
            api.fetchRegions().map { RegionsMapper.toDomain(it) }
        }
}
