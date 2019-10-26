package com.jcaique.dialetus.presentation.dialects

import com.jcaique.dialetus.domain.models.Region
import com.jcaique.dialetus.utils.dataflow.StateTransition
import com.jcaique.dialetus.utils.dataflow.UserInteraction

data class ShowDialects(val region: Region) : UserInteraction, StateTransition.Parameters

data class FilterDialects(val query: String) : UserInteraction, StateTransition.Parameters
