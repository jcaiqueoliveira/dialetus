package com.jcaique.dialetus.domain.dialects

import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.domain.models.DialectSlug

interface DialectsService {

    suspend fun getDialectsBy(region: String): List<Dialect>

    suspend fun searchDialects(query: String): Map<DialectSlug, List<Dialect>>
}
