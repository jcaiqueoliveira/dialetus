package com.jcaique.dialetus.domain.regions

import com.jcaique.dialetus.domain.models.Region

interface RegionsService {

    suspend fun fetchRegions(): List<Region>
}
