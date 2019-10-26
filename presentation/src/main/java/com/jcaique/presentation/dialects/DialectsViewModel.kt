package com.jcaique.presentation.dialects

import com.jcaique.domain.models.Dialect
import com.jcaique.utils.dataflow.StateMachine
import com.jcaique.utils.dataflow.StateTransition
import com.jcaique.utils.dataflow.UnsupportedUserInteraction
import com.jcaique.utils.dataflow.UserInteraction
import kotlinx.coroutines.coroutineScope

internal class DialectsViewModel(
    private val machine: StateMachine<DialectsPresentation>
    // TODO inject dialects service
) {

    // TODO mocked result
    private val dialects by lazy {
        listOf(
            Dialect(
                slug = "lasquei-em-banda",
                dialect = "Lasquei em banda",
                meanings = listOf("Sem pena", "Abrupto"),
                examples = listOf("Oxe man, lasquei foi em banda mermo, quis nem saber.")
            ),
            Dialect(
                slug = "lasquei-em-banda",
                dialect = "Lasquei em banda",
                meanings = listOf("Sem pena", "Abrupto"),
                examples = listOf("Oxe man, lasquei foi em banda mermo, quis nem saber.")
            ),
            Dialect(
                slug = "lasquei-em-banda",
                dialect = "Lasquei em banda",
                meanings = listOf("Sem pena", "Abrupto"),
                examples = listOf("Oxe man, lasquei foi em banda mermo, quis nem saber.")
            )
        )
    }

    fun bind() = machine.states()

    fun handle(interaction: UserInteraction) {
        interpret(interaction)
            .let(machine::consume)
    }

    private fun interpret(interaction: UserInteraction) =
        when (interaction) {
            is ShowDialects -> StateTransition(::showDialects, interaction)
            is FilterDialects -> StateTransition(::filterDialects, interaction)
            else -> throw UnsupportedUserInteraction
        }

    // TODO get dialects from service
    private suspend fun showDialects(
        parameters: StateTransition.Parameters
    ): DialectsPresentation = coroutineScope {
        val interaction = parameters as ShowDialects
        
        dialects
            .let(::DialectsPresentation)
    }
        
    private suspend fun filterDialects(
        parameters: StateTransition.Parameters
    ): DialectsPresentation = coroutineScope {
        val interaction = parameters as FilterDialects
        
        dialects
            .filter { it.dialect.contains(interaction.query, ignoreCase = true) }
            .let(::DialectsPresentation)
    }
}
