package com.jcaique.data.common

import com.jcaique.data.networking.RetrofitProvider
import com.jcaique.data.service.DialetusGateway
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource

internal class SetupScenario : ExternalResource() {

    private lateinit var server: MockWebServer
    internal lateinit var api: DialetusGateway

    override fun before() {
        super.before()
        server = MockWebServer()
        val url = server.url("/").toString()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()

        api = RetrofitProvider.provide(url, client).create(DialetusGateway::class.java)
    }

    override fun after() {
        server.shutdown()
        super.after()
    }

    fun defineScenario(status: Int, response: String = "") {

        server.enqueue(
            MockResponse().apply {
                setResponseCode(status)
                setBody(response)
            }
        )
    }
}
