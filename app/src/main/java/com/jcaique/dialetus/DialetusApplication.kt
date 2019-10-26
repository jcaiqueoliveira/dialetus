package com.jcaique.dialetus

import android.app.Application
import com.jcaique.dialetus.data.di.dataModule
import com.jcaique.dialetus.presentation.di.presentationModule
import kotlinx.coroutines.Dispatchers
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class DialetusApplication : Application(), KodeinAware {

    private val appModule = Kodein.Module(name = "application") {

        bind() from provider {
            this@DialetusApplication as Application
        }

        bind() from singleton {
            Dispatchers.IO
        }
    }

    override val kodein = ConfigurableKodein(mutable = true).apply {
        addImport(appModule)
        addImport(dataModule)
        addImport(presentationModule)
    }
}
