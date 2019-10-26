package com.jcaique.dialetus.presentation.di

import com.jcaique.dialetus.presentation.dialects.DialectsPresentation
import com.jcaique.dialetus.presentation.dialects.DialectsViewModel
import com.jcaique.dialetus.presentation.regions.RegionsPresentation
import com.jcaique.dialetus.presentation.regions.RegionsViewModel
import com.jcaique.dialetus.utils.KodeinTags
import com.jcaique.dialetus.utils.dataflow.ConfigChangesAwareStateContainer
import com.jcaique.dialetus.utils.dataflow.StateMachine
import com.jcaique.dialetus.utils.dataflow.TaskExecutor
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
