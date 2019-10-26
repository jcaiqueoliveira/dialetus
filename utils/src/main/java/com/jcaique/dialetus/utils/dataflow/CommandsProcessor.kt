package com.jcaique.dialetus.utils.dataflow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

class CommandsProcessor(
  private val executor: TaskExecutor
) {

    @ExperimentalCoroutinesApi
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
