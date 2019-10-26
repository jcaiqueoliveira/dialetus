package com.jcaique.presentation.di

import com.jcaique.presentation.dialects.DialectsPresentation
import com.jcaique.presentation.dialects.DialectsViewModel
import com.jcaique.presentation.regions.RegionsPresentation
import com.jcaique.presentation.regions.RegionsViewModel
import com.jcaique.utils.KodeinTags
import com.jcaique.utils.dataflow.ConfigChangesAwareStateContainer
import com.jcaique.utils.dataflow.StateMachine
import com.jcaique.utils.dataflow.TaskExecutor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val presentationModule = Kodein.Module(name = "presentation") {

    bind() from provider {
        val stateContainer =
            ConfigChangesAwareStateContainer<RegionsPresentation>(
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

    bind() from provider {
        val stateContainer =
            ConfigChangesAwareStateContainer<DialectsPresentation>(
                host = instance(KodeinTags.hostActivity)
            )
        val stateMachine = StateMachine(
            container = stateContainer,
            executor = TaskExecutor.Concurrent(
                scope = stateContainer.emissionScope,
                dispatcher = instance()
            )
        )

        DialectsViewModel(
            machine = stateMachine,
            service = instance()
        )
    }
}
