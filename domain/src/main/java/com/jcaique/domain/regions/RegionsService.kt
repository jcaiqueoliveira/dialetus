package com.jcaique.domain.regions

import com.jcaique.domain.models.Region

interface RegionsService {

    suspend fun fetchRegions(): List<Region>
}
