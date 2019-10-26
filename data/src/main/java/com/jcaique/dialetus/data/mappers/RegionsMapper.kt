package com.jcaique.dialetus.data.mappers

import com.jcaique.dialetus.data.responses.RegionResponse
import com.jcaique.dialetus.domain.models.Region

internal object RegionsMapper {
    fun toDomain(response: RegionResponse): Region =
        Region(response.name, response.total)
}
