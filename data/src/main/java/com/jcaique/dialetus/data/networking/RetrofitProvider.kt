package com.jcaique.dialetus.data.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit

internal object RetrofitProvider {

    private const val APPLICATION_JSON = "application/json"

    private val json by lazy {
        Json.nonstrict
    }
    private val contentType by lazy {
        APPLICATION_JSON.toMediaTypeOrNull()!!
    }

    fun provide(url: String, okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(
            json.asConverterFactory(
                contentType
            ))
        .client(okHttpClient)
        .build()
}
