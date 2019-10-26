package com.jcaique.dialetus.data.dialects

import com.jcaique.dialetus.data.common.SetupScenario
import com.jcaique.dialetus.data.common.loadFile
import com.jcaique.dialetus.data.service.dialects.DialectsInfrastructure
import com.jcaique.dialetus.domain.dialects.DialectsService
import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.domain.models.DialectSlug
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
            service.getDialectsBy("baianes")
        }

        assertThat(execution).isEqualTo(expected)
    }

    @Test
    fun `should search dialects`() {

        scenario.defineScenario(
            status = 200,
            response = loadFile("search_dialects.json")
        )

        val expected = mapOf(
            DialectSlug("baianes") to listOf(
                Dialect(
                    slug = "relaxe-mo-fiu",
                    dialect = "Relaxe mô fiu.",
                    meanings = listOf("Sem problemas", "Fique tranquilo"),
                    examples = listOf("Ô vei, relaxe mô fiu todo nervoso ele.")
                )
            )
        )

        val execution = runBlocking {
            service.searchDialects("Relaxe")
        }

        assertThat(execution).isEqualTo(expected)
    }
}
