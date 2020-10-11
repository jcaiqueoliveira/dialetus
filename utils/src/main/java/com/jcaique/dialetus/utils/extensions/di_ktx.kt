package com.jcaique.dialetus.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.jcaique.dialetus.utils.KodeinTags.hostActivity
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.provider

fun AppCompatActivity.selfInject(bindings: DI.MainBuilder.() -> Unit = {}) = DI.lazy {

    val parentKodein = (applicationContext as DIAware).di

    extend(parentKodein)

    bind<FragmentActivity>(tag = hostActivity) with provider {
        this@selfInject
    }

    bindings.invoke(this)
}
