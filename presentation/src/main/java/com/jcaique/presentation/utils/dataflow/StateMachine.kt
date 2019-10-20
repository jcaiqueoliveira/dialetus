package com.jcaique.presentation.utils.dataflow

internal class StateMachine<T>(
    private val container: StateContainer<T>,
    private val executor: TaskExecutor
) {

    fun states() = container.observableStates()

    fun consume(execution: StateTransition<T>) =
        executor.execute {
            wrapWithStates(execution)
        }

    private suspend fun wrapWithStates(execution: StateTransition<T>) {
        val first = executionStarted()
        moveTo(first)
        val next = executeWith(execution)
        moveTo(next)
    }

    private suspend fun executeWith(transition: StateTransition<T>): ViewState<T> {
        return try {
            val execution =
                when (transition) {
                    is StateTransition.Unparametrized -> transition.task.invoke()
                    is StateTransition.Parametrized -> with(transition) {
                        this.task.invoke(
                            parameters
                        )
                    }
                }
            ViewState.Success(execution)
        } catch (error: Throwable) {
            ViewState.Failed(error)
        }
    }

    private fun executionStarted() =
        when (val state = container.current()) {
            is ViewState.FirstLaunch,
            is ViewState.Failed -> ViewState.Loading.FromEmpty
            else -> restoreIfSuccess(state)
        }

    private fun restoreIfSuccess(state: ViewState<T>) =
        when (state) {
            is ViewState.Success -> ViewState.Loading.FromPrevious(state.value)
            else -> state
        }

    private suspend fun moveTo(state: ViewState<T>) {
        container.store(state)
    }
}
