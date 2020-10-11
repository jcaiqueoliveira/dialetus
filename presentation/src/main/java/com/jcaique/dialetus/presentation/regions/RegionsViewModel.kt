package com.jcaique.dialetus.presentation.regions

import androidx.lifecycle.ViewModel
import cafe.adriel.dalek.Dalek
import cafe.adriel.dalek.DalekEvent
import com.jcaique.dialetus.domain.regions.RegionsService
import kotlinx.coroutines.flow.Flow

internal class RegionsViewModel(
  private val service: RegionsService
) : ViewModel() {

    fun showRegions(): Flow<DalekEvent<RegionsPresentation>> =
        Dalek {
            service
                .fetchRegions()
                .map { it.copy(it.name.capitalize()) }
                .let(::RegionsPresentation)
        }
}
