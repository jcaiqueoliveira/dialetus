package com.jcaique.dialetus.data.mappers

import com.jcaique.dialetus.data.responses.DialectResponse
import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.domain.models.DialectSlug

internal object DialectsMapper {

    fun toDomain(response: DialectResponse): Dialect =
        Dialect(
            response.slug,
            response.dialect,
            response.meanings,
            response.examples
        )

    fun toDomain(response: Map.Entry<String, List<DialectResponse>>): Pair<DialectSlug, List<Dialect>> =
        DialectSlug(response.key) to response.value.map(
            DialectsMapper::toDomain)
}
