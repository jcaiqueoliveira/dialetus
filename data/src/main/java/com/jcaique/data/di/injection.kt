package com.jcaique.data.di

import com.jcaique.data.Consts.URL
import com.jcaique.data.networking.OkHttpClientProvider
import com.jcaique.data.networking.RetrofitProvider
import com.jcaique.data.service.DialetusGateway
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton


private val interceptors =
    listOf(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })

val dataModule = Kodein.Module(name = "network") {

    bind<DialetusGateway>() with singleton {
        val client = OkHttpClientProvider.provide(interceptors)
        val retrofit = RetrofitProvider.provide(URL, client)
        retrofit.create(DialetusGateway::class.java)
    }
}
