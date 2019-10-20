package com.jcaique.data.service.dialects

import com.jcaique.data.mappers.DialectsMapper
import com.jcaique.data.networking.executionHandler
import com.jcaique.data.service.DialetusGateway
import com.jcaique.domain.dialects.DialectsService
import com.jcaique.domain.models.Dialect

internal class DialectsInfrastructure(private val api: DialetusGateway) : DialectsService {
    override suspend fun getDialectBy(region: String): List<Dialect> = executionHandler {
        api.getDialectsBy(region).map { DialectsMapper.toDomain(it) }
    }
}
