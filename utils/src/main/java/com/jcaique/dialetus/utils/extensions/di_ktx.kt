package com.jcaique.dialetus.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.jcaique.dialetus.utils.KodeinTags.hostActivity
import org.kodein.di.*

fun AppCompatActivity.selfInject(bindings: DI.MainBuilder.() -> Unit = {}) = DI.lazy {

    val parentKodein = (applicationContext as DIAware).di

    extend(parentKodein)

    bind<FragmentActivity>(tag = hostActivity) with provider {
        this@selfInject
    }

    bindings.invoke(this)
}
