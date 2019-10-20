package com.jcaique.presentation.di

import com.jcaique.presentation.regions.RegionsPresentation
import com.jcaique.presentation.regions.RegionsViewModel
import com.jcaique.presentation.utils.KodeinTags
import com.jcaique.presentation.utils.dataflow.ConfigChangesAwareStateContainer
import com.jcaique.presentation.utils.dataflow.StateMachine
import com.jcaique.presentation.utils.dataflow.TaskExecutor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val presentationModule = Kodein.Module(name = "presentation") {

    bind() from provider {
        val stateContainer = ConfigChangesAwareStateContainer<RegionsPresentation>(
            host = instance(KodeinTags.hostActivity)
        )
        val stateMachine = StateMachine(
            container = stateContainer,
            executor = TaskExecutor.Concurrent(
                scope = stateContainer.emissionScope,
                dispatcher = instance()
            )
        )
        
        RegionsViewModel(
            service = instance(),
            machine = stateMachine
        )
    }
}
