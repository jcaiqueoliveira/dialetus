package com.jcaique.dialetus.presentation.dialects

import com.jcaique.dialetus.domain.dialects.DialectsService
import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.utils.dataflow.StateMachine
import com.jcaique.dialetus.utils.dataflow.StateTransition
import com.jcaique.dialetus.utils.dataflow.UnsupportedUserInteraction
import com.jcaique.dialetus.utils.dataflow.UserInteraction
import com.jcaique.dialetus.utils.extensions.normalize
import kotlinx.coroutines.coroutineScope

internal class DialectsViewModel(
    private val machine: StateMachine<DialectsPresentation>,
    private val service: DialectsService
) {

    private var dialects = emptyList<Dialect>()

    fun bind() = machine.states()

    fun handle(interaction: UserInteraction) {
        interpret(interaction)
            .let(machine::consume)
    }

    private fun interpret(interaction: UserInteraction) =
        when (interaction) {
            is ShowDialects -> StateTransition(
                ::showDialects,
                interaction
            )
            is FilterDialects -> StateTransition(
                ::filterDialects,
                interaction
            )
            else -> throw UnsupportedUserInteraction
        }

    private suspend fun showDialects(
        parameters: StateTransition.Parameters
    ): DialectsPresentation = coroutineScope {
        val interaction = parameters as ShowDialects

        service
            .getDialectsBy(interaction.region.name.toLowerCase())
            .let(::DialectsPresentation)
    }
        
    private suspend fun filterDialects(
        parameters: StateTransition.Parameters
    ): DialectsPresentation = coroutineScope {
        val interaction = parameters as FilterDialects
        
        dialects
            .filter { 
                it.dialect
                    .normalize()
                    .contains(
                        other = interaction.query.normalize(), 
                        ignoreCase = true
                    ) 
            }
            .let(::DialectsPresentation)
    }
}
