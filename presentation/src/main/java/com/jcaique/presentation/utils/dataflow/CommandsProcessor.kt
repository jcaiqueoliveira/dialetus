package com.jcaique.presentation.utils.dataflow

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

internal class CommandsProcessor(
  private val executor: TaskExecutor
) {

    private val broadcaster = ConflatedBroadcastChannel<ViewCommand>()

    fun commands() = broadcaster.asFlow()

    fun process(trigger: CommandTrigger) =
        executor.execute {
            val command = when (trigger) {
                is CommandTrigger.Unparametrized -> trigger.task.invoke()
                is CommandTrigger.Parametrized -> with(trigger) { task.invoke(parameters) }
            }
            broadcaster.send(command)
        }
}
