package com.jcaique.data.service

import com.jcaique.data.responses.DialectResponse
import com.jcaique.data.responses.RegionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface DialetusGateway {

    @GET("regions")
    suspend fun fetchRegions() : List<RegionResponse>

    @GET("regions/{region}/dialects")
    suspend fun getDialectsBy(@Path("region") region: String) : List<DialectResponse>

    @GET("search")
    suspend fun searchDialects(@Query("q") query: String) : Map<String, List<DialectResponse>>
}
