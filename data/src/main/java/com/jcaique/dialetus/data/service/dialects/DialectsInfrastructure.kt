package com.jcaique.dialetus.data.service.dialects

import com.jcaique.dialetus.data.mappers.DialectsMapper
import com.jcaique.dialetus.data.networking.executionHandler
import com.jcaique.dialetus.data.service.DialetusGateway
import com.jcaique.dialetus.domain.dialects.DialectsService
import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.domain.models.DialectSlug

internal class DialectsInfrastructure(private val api: DialetusGateway) :
    DialectsService {

    override suspend fun getDialectsBy(region: String): List<Dialect> =
        executionHandler {
            api.getDialectsBy(region)
                .map(DialectsMapper::toDomain)
        }

    override suspend fun searchDialects(query: String): Map<DialectSlug, List<Dialect>> =
        executionHandler {
            api.searchDialects(query)
                .map(DialectsMapper::toDomain)
                .toMap()
        }
}
