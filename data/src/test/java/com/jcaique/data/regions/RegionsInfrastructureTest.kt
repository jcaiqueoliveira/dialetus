package com.jcaique.data.regions

import com.jcaique.data.common.SetupScenario
import com.jcaique.data.common.loadFile
import com.jcaique.data.service.regions.RegionsInfrastructure
import com.jcaique.domain.models.Region
import com.jcaique.domain.regions.RegionsService
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegionsInfrastructureTest {

    @get:Rule
    private val scenario = SetupScenario()

    private lateinit var service: RegionsService

    @Before
    fun setup(){
        service = RegionsInfrastructure(scenario.api)
    }

    @Test
    fun `should fetch regions`(){

        scenario.defineScenario(
            status = 200,
            response = loadFile("regions.json")
        )

        val execution = runBlocking {
            service.fetchRegions()
        }

        val expected = listOf(
            Region(
                name = "baianes",
                total = 42
            ),
            Region(
                name = "carioques",
                total = 14
            ),
            Region(
                name = "paulistes",
                total = 16
            ),
            Region (
                name = "pernambuques",
                total = 17
            ),
            Region(
                name = "sergipanes",
                total = 11
            )
        )

        assertThat(execution).isEqualTo(expected)

    }
}
