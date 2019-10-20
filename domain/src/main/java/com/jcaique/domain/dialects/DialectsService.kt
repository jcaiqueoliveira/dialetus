package com.jcaique.domain.dialects

import com.jcaique.domain.models.Dialect
import com.jcaique.domain.models.DialectSlug

interface DialectsService {

    suspend fun getDialectsBy(region: String): List<Dialect>

    suspend fun searchDialects(query: String): Map<DialectSlug, List<Dialect>>
}
