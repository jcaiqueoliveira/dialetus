package com.jcaique.data.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class DialectResponse(
    val slug: String,
    val dialect: String,
    val meanings: List<String>,
    val examples: List<String>
)
