package com.jcaique.data.service.regions

import com.jcaique.data.mappers.RegionsMapper
import com.jcaique.data.networking.executionHandler
import com.jcaique.data.service.DialetusGateway
import com.jcaique.domain.models.Region
import com.jcaique.domain.regions.RegionsService

internal class RegionsInfrastructure(private val api: DialetusGateway) : RegionsService {

    override suspend fun fetchRegions(): List<Region> = executionHandler {
        api.fetchRegions() .map { RegionsMapper.toDomain(it) }
    }

}
