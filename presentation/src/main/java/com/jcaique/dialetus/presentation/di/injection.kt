package com.jcaique.dialetus.presentation.di

import com.jcaique.dialetus.presentation.dialects.DialectsViewModel
import com.jcaique.dialetus.presentation.regions.RegionsViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val presentationModule = Kodein.Module(name = "presentation") {

    bind() from provider {
        RegionsViewModel(
            service = instance()
        )
    }

    bind() from provider {
        DialectsViewModel(
            service = instance()
        )
    }
}
