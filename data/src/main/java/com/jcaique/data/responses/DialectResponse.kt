package com.jcaique.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class DialectResponse(
    val slug: String,
    val dialect: String,
    val meanings: List<String>,
    val examples: List<String>
)
