package com.jcaique.presentation.dialects

import com.jcaique.domain.models.Dialect
import com.jcaique.presentation.utils.dataflow.StateMachine
import com.jcaique.presentation.utils.dataflow.StateTransition
import com.jcaique.presentation.utils.dataflow.UnsupportedUserInteraction
import com.jcaique.presentation.utils.dataflow.UserInteraction

internal class DialectsViewModel(
    private val machine: StateMachine<DialectsPresentation>
    // TODO inject dialects service
) {

    fun bind() = machine.states()

    fun handle(interaction: UserInteraction) =
        interpret(interaction)
            .let(machine::consume)

    private fun interpret(interaction: UserInteraction) =
        when (interaction) {
            UserInteraction.OpenedScreen,
            UserInteraction.RequestedFreshContent -> StateTransition(::showDialects)
            else -> throw UnsupportedUserInteraction
        }

    // TODO mocked result
    private suspend fun showDialects() =
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
            ),
            Dialect(
                slug = "lasquei-em-banda",
                dialect = "Lasquei em banda",
                meanings = listOf("Sem pena", "Abrupto"),
                examples = listOf("Oxe man, lasquei foi em banda mermo, quis nem saber.")
            )
        ).let(::DialectsPresentation)
}
