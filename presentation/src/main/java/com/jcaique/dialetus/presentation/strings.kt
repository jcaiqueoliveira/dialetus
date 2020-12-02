package com.jcaique.dialetus.presentation

import androidx.compose.runtime.ambientOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

val StringsAmbient = ambientOf<Strings> { error("Strings not initialized") }

data class Strings(
  val appName: String = "Dialetus",
  val meaning: String = "Significado",
  val examples: String = "Exemplos",
  val search: String = "Pesquisar",
  val moreDialectsOn: String = "Mais dialetos em %s",
  val tryAgain: String = "Tentar novamente",
  val noDialectFoundTitle: String = "Nenhum dialeto cadastrado",
  val noDialectFoundSubtitle: String = "Não foi encontrado nenhum dialeto ou região.",
  val defaultError: String = "Ops, algo deu errado",
  val loadRegionsError: String = "Não conseguimos carregar as regiões"
)
