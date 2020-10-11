package com.jcaique.dialetus.presentation.dialects

import androidx.lifecycle.ViewModel
import cafe.adriel.dalek.Dalek
import cafe.adriel.dalek.DalekEvent
import com.jcaique.dialetus.domain.dialects.DialectsService
import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.domain.models.Region
import com.jcaique.dialetus.presentation.ktx.matches
import kotlinx.coroutines.flow.Flow

internal class DialectsViewModel(
    private val service: DialectsService
) : ViewModel()  {

    // TODO cache dialects elsewhere
    private var dialects = emptyList<Dialect>()
    
    fun getDialects(region: Region): Flow<DalekEvent<DialectsPresentation>> =
        Dalek {
            service
                .getDialectsBy(region.name.toLowerCase())
                .also(::dialects::set)
                .let(::DialectsPresentation)
        }
        
    fun filterDialects(query: String): Flow<DalekEvent<DialectsPresentation>> =
        Dalek {
            dialects
                .filter { it.matches(query) }
                .let(::DialectsPresentation)
        }
}
