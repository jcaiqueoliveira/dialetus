package com.jcaique.data.di

import com.jcaique.data.networking.OkHttpClientProvider
import com.jcaique.data.networking.RetrofitProvider
import com.jcaique.data.service.DialetusGateway
import okhttp3.logging.HttpLoggingInterceptor

// TODO("Use kodein to dependency injection")
val baseUrl = "https://dialetus-service.herokuapp.com/"

val interceptors = listOf(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
val okHttpClient = OkHttpClientProvider.provide(interceptors)
val retrofit = RetrofitProvider.provide(baseUrl, okHttpClient)

val service = retrofit.create(DialetusGateway::class.java)
