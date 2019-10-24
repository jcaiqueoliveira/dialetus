package com.jcaique.utils.dataflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

interface StateContainer<T> : ViewStateRegistry<T>, ViewStatesEmitter<T> {

    class Unbounded<T>(scopeToBound: CoroutineScope) : StateContainer<T> {

        private val broadcaster by lazy {
            ConflatedBroadcastChannel<ViewState<T>>(ViewState.FirstLaunch)
        }

        override val emissionScope = scopeToBound

        override fun observableStates() = broadcaster.asFlow()

        override fun current(): ViewState<T> = broadcaster.value

        override suspend fun store(state: ViewState<T>) {
            broadcaster.send(state)
        }
    }
}
