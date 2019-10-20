package com.jcaique.domain.dialects

import com.jcaique.domain.models.Dialect

interface DialectsService {
    suspend fun getDialectBy(region: String) : List<Dialect>
}
