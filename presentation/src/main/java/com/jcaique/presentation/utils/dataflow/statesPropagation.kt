package com.jcaique.presentation.utils.dataflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

internal interface ViewStatesEmitter<T> {

    fun observableStates(): Flow<ViewState<T>>

    val emissionScope: CoroutineScope
}

internal interface ViewStateRegistry<T> {

    fun current(): ViewState<T>

    suspend fun store(state: ViewState<T>)
}
