package com.jcaique.data.networking

import okhttp3.Interceptor
import okhttp3.OkHttpClient

internal object OkHttpClientProvider {

    fun provide(interceptors: List<Interceptor>) = OkHttpClient.Builder().apply {
        interceptors.forEach {
            addInterceptor(it)
        }
    }
        .build()

}
