package com.jcaique.dialetus.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.jcaique.dialetus.utils.KodeinTags.hostActivity
import com.jcaique.dialetus.utils.dataflow.ConfigChangesAwareStateContainer
import com.jcaique.dialetus.utils.dataflow.StateContainer
import com.jcaique.dialetus.utils.dataflow.StateMachine
import com.jcaique.dialetus.utils.dataflow.TaskExecutor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.bindings.NoArgBindingKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

fun AppCompatActivity.selfInject(bindings: Kodein.MainBuilder.() -> Unit = {}) = Kodein.lazy {

    val parentKodein = (applicationContext as KodeinAware).kodein

    extend(parentKodein)

    bind<FragmentActivity>(tag = hostActivity) with provider {
        this@selfInject
    }

    bindings.invoke(this)
}

fun <T> NoArgBindingKodein<*>.newStateContainer(tag: String) =
    ConfigChangesAwareStateContainer<T>(
        host = instance(tag)
    )

fun <T> NoArgBindingKodein<*>.newStateMachine(stateContainer: StateContainer<T>) =
    StateMachine(
        container = stateContainer,
        executor = TaskExecutor.Concurrent(
            scope = stateContainer.emissionScope,
            dispatcher = instance()
        )
    )
