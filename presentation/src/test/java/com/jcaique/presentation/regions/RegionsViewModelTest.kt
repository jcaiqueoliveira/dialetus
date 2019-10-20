package com.jcaique.presentation.regions

import com.jcaique.domain.errors.UnexpectedResponse
import com.jcaique.domain.models.Region
import com.jcaique.domain.regions.RegionsService
import com.jcaique.presentation.common.CoroutinesTestHelper
import com.jcaique.presentation.common.FlowTest.Companion.flowTest
import com.jcaique.presentation.utils.dataflow.StateContainer
import com.jcaique.presentation.utils.dataflow.StateMachine
import com.jcaique.presentation.utils.dataflow.TaskExecutor
import com.jcaique.presentation.utils.dataflow.UserInteraction
import com.jcaique.presentation.utils.dataflow.ViewState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
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
        val stateMachine = StateMachine<RegionsPresentation>(
            executor = TaskExecutor.Synchronous(helper.scope),
            container = StateContainer.Unbounded(helper.scope)
        )

        viewModel = RegionsViewModel(service, stateMachine)
    }

    @Test
    fun `should report failure when fetching regions from remote`() {

        // Given
        flowTest(viewModel.bind()) {

            triggerEmissions {

                // When
                whenever(service.fetchRegions())
                    .thenAnswer { throw UnexpectedResponse }

                // And
                viewModel.handle(UserInteraction.OpenedScreen)
            }

            afterCollect { emissions ->

                // Then
                val viewStates = listOf(
                    ViewState.FirstLaunch,
                    ViewState.Loading.FromEmpty,
                    ViewState.Failed(UnexpectedResponse)
                )

                assertThat(emissions).isEqualTo(viewStates)
            }
        }
    }

    @Test
    fun `should fetch regions from remote data source with success`() {

        // Given

        val regions = listOf(
            Region(
                name = "baianes",
                total = 1
            )
        )

        val presentation = RegionsPresentation(regions)

        flowTest(viewModel.bind()) {

            triggerEmissions {

                // When
                whenever(service.fetchRegions()).thenReturn(regions)

                // And
                viewModel.handle(UserInteraction.OpenedScreen)
            }

            afterCollect { emissions ->

                // Then
                val viewStates = listOf(
                    ViewState.FirstLaunch,
                    ViewState.Loading.FromEmpty,
                    ViewState.Success(presentation)
                )

                assertThat(emissions).isEqualTo(viewStates)
            }
        }
    }
}
