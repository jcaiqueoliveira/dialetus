package com.jcaique.dialetus.presentation.regions

import cafe.adriel.dalek.Failure
import cafe.adriel.dalek.Finish
import cafe.adriel.dalek.Start
import cafe.adriel.dalek.Success
import com.jcaique.dialetus.domain.errors.UnexpectedResponse
import com.jcaique.dialetus.domain.models.Region
import com.jcaique.dialetus.domain.regions.RegionsService
import com.jcaique.dialetus.presentation.common.CoroutinesTestHelper
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegionsViewModelTest {

    @get:Rule
    val helper = CoroutinesTestHelper()

    private val service = mock<RegionsService>()

    private lateinit var viewModel: RegionsViewModel

    @Before
    fun `before each test`() {
        viewModel = RegionsViewModel(service)
    }

    @Test
    fun `should report failure when fetching regions from remote`() = runBlockingTest {
        service.stub {
            onBlocking { fetchRegions() } doThrow UnexpectedResponse
        }

        val expected = listOf(
            Start,
            Failure(UnexpectedResponse),
            Finish
        )

        val result = runBlocking {
            viewModel
                .showRegions()
                .toList()
        }

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should fetch regions from remote data source with success`() = runBlockingTest {
        val regions = listOf(
            Region(
                name = "Baianes",
                total = 1
            )
        )
        val presentation = RegionsPresentation(regions)

        service.stub {
            onBlocking { fetchRegions() } doReturn regions
        }

        val expected = listOf(
            Start,
            Success(presentation),
            Finish
        )

        val result = runBlocking {
            viewModel
                .showRegions()
                .toList()
        }

        assertThat(result).isEqualTo(expected)
    }
}
