package com.jcaique.dialetus

import android.app.Application
import com.jcaique.dialetus.data.di.dataModule
import com.jcaique.dialetus.presentation.di.presentationModule
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.conf.ConfigurableDI
import org.kodein.di.provider
import org.kodein.di.singleton

class DialetusApplication : Application(), DIAware {

    private val appModule = DI.Module(name = "application") {

        bind() from provider {
            this@DialetusApplication as Application
        }

        bind() from singleton {
            Dispatchers.IO
        }
    }
    override val di: DI = ConfigurableDI(mutable = true).apply {
        addImport(appModule)
        addImport(dataModule)
        addImport(presentationModule)
    }
}
