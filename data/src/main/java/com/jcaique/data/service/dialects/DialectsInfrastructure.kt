package com.jcaique.data.service.dialects

import com.jcaique.data.mappers.DialectsMapper
import com.jcaique.data.networking.executionHandler
import com.jcaique.data.service.DialetusGateway
import com.jcaique.domain.dialects.DialectsService
import com.jcaique.domain.models.Dialect
import com.jcaique.domain.models.DialectSlug

internal class DialectsInfrastructure(private val api: DialetusGateway) : DialectsService {
    
    
    override suspend fun getDialectsBy(region: String): List<Dialect> = executionHandler {
        api.getDialectsBy(region)
            .map(DialectsMapper::toDomain)
    }

    override suspend fun searchDialects(query: String): Map<DialectSlug, List<Dialect>> = executionHandler {
        api.searchDialects(query)
            .map(DialectsMapper::toDomain)
            .toMap()
    }
}
