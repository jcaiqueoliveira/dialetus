package com.jcaique.dialetus.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.jcaique.dialetus.utils.KodeinTags.hostActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

fun AppCompatActivity.selfInject(bindings: Kodein.MainBuilder.() -> Unit = {}) = Kodein.lazy {

    val parentKodein = (applicationContext as KodeinAware).kodein

    extend(parentKodein)

    bind<FragmentActivity>(tag = hostActivity) with provider {
        this@selfInject
    }

    bindings.invoke(this)
}
