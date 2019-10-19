package com.jcaique.data.di

import com.jcaique.data.networking.OkHttpClientProvider
import com.jcaique.data.networking.RetrofitProvider
import com.jcaique.data.service.DialetusGateway
import okhttp3.logging.HttpLoggingInterceptor

// TODO("Use kodein to dependency injection")
private const val baseUrl = "https://dialetus-service.herokuapp.com/"

private val interceptors = listOf(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
private val okHttpClient = OkHttpClientProvider.provide(interceptors)
private val retrofit = RetrofitProvider.provide(baseUrl, okHttpClient)

internal val service = retrofit.create(DialetusGateway::class.java)
