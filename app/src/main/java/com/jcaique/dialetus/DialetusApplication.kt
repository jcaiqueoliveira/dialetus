package com.jcaique.dialetus

import android.app.Application
import com.jcaique.data.di.dataModule
import com.jcaique.presentation.di.presentationModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

class DialetusApplication : Application(), KodeinAware {

    private val appModule = Kodein.Module(name = "application") {

        bind() from provider {
            this@DialetusApplication as Application
        }
    }

    override val kodein = ConfigurableKodein(mutable = true).apply {
        addImport(appModule)
        addImport(dataModule)
        addImport(presentationModule)
    }
}
