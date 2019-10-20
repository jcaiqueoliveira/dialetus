package com.jcaique.presentation.dataflow

import com.jcaique.presentation.common.CoroutinesTestHelper
import com.jcaique.presentation.common.test
import com.jcaique.presentation.utils.dataflow.CommandTrigger
import com.jcaique.presentation.utils.dataflow.CommandsProcessor
import com.jcaique.presentation.utils.dataflow.TaskExecutor
import com.jcaique.presentation.utils.dataflow.ViewCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class CommandsProcessorTests {

    @get:Rule val helper = CoroutinesTestHelper()

    private lateinit var processor: CommandsProcessor

    @Before fun `before each test`() {
        processor = CommandsProcessor(
            executor = TaskExecutor.Synchronous(helper.scope)
        )
    }

    @Test fun `should trigger commands`() {
        with(processor) {
            commands().test {

                triggerEmissions {
                    process(
                        CommandTrigger(::generateCommand)
                    )
                }

                triggerEmissions {
                    process(
                        CommandTrigger(::generateCommand, Interaction)
                    )
                }

                afterCollect { emissions ->

                    val commands = listOf(
                        Finish,
                        FinishWithMessage(MESSAGE)
                    )

                    assertThat(emissions).isEqualTo(commands)
                }
            }
        }
    }

    private suspend fun generateCommand() =
        suspendCoroutine<ViewCommand> { continuation ->
            continuation.resume(Finish)
        }

    private suspend fun generateCommand(params: CommandTrigger.Parameters) =
        suspendCoroutine<ViewCommand> { continuation ->
            val interaction = params as Interaction
            continuation.resume(FinishWithMessage(interaction.MESSAGE))
        }

    private companion object Interaction : CommandTrigger.Parameters {
        const val MESSAGE = "Data from interaction"
    }

    object Finish : ViewCommand()
    data class FinishWithMessage(val message: String) : ViewCommand()
}
