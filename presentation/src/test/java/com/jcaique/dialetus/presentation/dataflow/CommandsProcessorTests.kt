package com.jcaique.dialetus.presentation.dataflow

import com.jcaique.dialetus.presentation.common.CoroutinesTestHelper
import com.jcaique.dialetus.presentation.common.test
import com.jcaique.dialetus.utils.dataflow.CommandTrigger
import com.jcaique.dialetus.utils.dataflow.CommandsProcessor
import com.jcaique.dialetus.utils.dataflow.TaskExecutor
import com.jcaique.dialetus.utils.dataflow.ViewCommand
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class CommandsProcessorTests {

    @get:Rule
    val helper = CoroutinesTestHelper()

    private lateinit var processor: CommandsProcessor

    @Before
    fun `before each test`() {
        processor = CommandsProcessor(
            executor = TaskExecutor.Synchronous(helper.scope)
        )
    }

    @Test
    fun `should trigger commands`() {
        with(processor) {
            commands().test {

                triggerEmissions {
                    process(
                        CommandTrigger(::generateCommand)
                    )
                }

                triggerEmissions {
                    process(
                        CommandTrigger(
                            ::generateCommand,
                            Interaction
                        )
                    )
                }

                afterCollect { emissions ->

                    val commands = listOf(
                        Finish,
                        FinishWithMessage(
                            MESSAGE
                        )
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
            continuation.resume(
                FinishWithMessage(
                    MESSAGE
                )
            )
        }

    private companion object Interaction : CommandTrigger.Parameters {
        const val MESSAGE = "Data from interaction"
    }

    object Finish : ViewCommand()
    data class FinishWithMessage(val message: String) : ViewCommand()
}
