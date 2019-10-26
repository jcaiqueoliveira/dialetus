package com.jcaique.presentation.dialects

import com.jcaique.domain.models.Region
import com.jcaique.presentation.utils.dataflow.StateTransition
import com.jcaique.presentation.utils.dataflow.UserInteraction

data class ShowDialects(val region: Region) : UserInteraction, StateTransition.Parameters

data class FilterDialects(val query: String) : UserInteraction, StateTransition.Parameters
