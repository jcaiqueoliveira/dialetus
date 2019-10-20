package com.jcaique.data.mappers

import com.jcaique.data.responses.DialectResponse
import com.jcaique.domain.models.Dialect

object DialectsMapper {

    fun toDomain(response: DialectResponse): Dialect =
        Dialect(response.slug, response.dialect, response.meanings, response.examples)
}
