package com.jcaique.data.dialects

import com.jcaique.data.common.SetupScenario
import com.jcaique.data.common.loadFile
import com.jcaique.data.service.dialects.DialectsInfrastructure
import com.jcaique.domain.dialects.DialectsService
import com.jcaique.domain.models.Dialect
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DialectsInfrastructureTests {

    @get:Rule
    internal val scenario = SetupScenario()

    private lateinit var service: DialectsService

    @Before
    fun setup() {
        service = DialectsInfrastructure(scenario.api)
    }

    @Test
    fun `should retrieve dialects`() {

        scenario.defineScenario(
            status = 200,
            response = loadFile("dialects.json")
        )

        val expected = listOf(
            Dialect(
                slug = "relaxe-mo-fiu",
                dialect = "Relaxe mô fiu.",
                meanings = listOf("Sem problemas", "Fique tranquilo"),
                examples = listOf("Ô vei, relaxe mô fiu todo nervoso ele.")
            )
        )

        val execution = runBlocking {
            service.getDialectBy("baianes")
        }

        assertThat(execution).isEqualTo(expected)
    }
}
