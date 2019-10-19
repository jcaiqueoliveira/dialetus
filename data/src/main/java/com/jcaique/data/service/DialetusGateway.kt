package com.jcaique.data.service

import com.jcaique.data.responses.RegionResponse
import retrofit2.http.GET

internal interface DialetusGateway {

    @GET("regions")
    suspend fun fetchRegions() : List<RegionResponse>
}
