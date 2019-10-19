package com.jcaique.data.mappers

import com.jcaique.data.responses.RegionResponse
import com.jcaique.domain.models.Region

internal object RegionsMapper {
    fun toDomain(response: RegionResponse): Region = Region(response.name, response.total)
}
