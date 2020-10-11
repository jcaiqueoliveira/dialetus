package com.jcaique.dialetus.presentation.ktx

import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.utils.extensions.normalize

internal fun Dialect.matches(query: String): Boolean =
    dialect
        .normalize()
        .contains(other = query.normalize(), ignoreCase = true)
