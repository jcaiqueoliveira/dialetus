package com.jcaique.dialetus.presentation.di

import com.jcaique.dialetus.presentation.dialects.DialectsPresentation
import com.jcaique.dialetus.presentation.dialects.DialectsViewModel
import com.jcaique.dialetus.presentation.regions.RegionsPresentation
import com.jcaique.dialetus.presentation.regions.RegionsViewModel
import com.jcaique.dialetus.utils.KodeinTags
import com.jcaique.dialetus.utils.extensions.newStateContainer
import com.jcaique.dialetus.utils.extensions.newStateMachine
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val presentationModule = Kodein.Module(name = "presentation") {

    bind() from provider {
        val stateContainer = newStateContainer<RegionsPresentation>(KodeinTags.hostActivity)
        val stateMachine = newStateMachine(stateContainer)

        RegionsViewModel(
            service = instance(),
            machine = stateMachine
        )
    }

    bind() from provider {
        val stateContainer = newStateContainer<DialectsPresentation>(KodeinTags.hostActivity)
        val stateMachine = newStateMachine(stateContainer)

        DialectsViewModel(
            machine = stateMachine,
            service = instance()
        )
    }
}
