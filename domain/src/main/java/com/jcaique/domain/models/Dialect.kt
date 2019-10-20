package com.jcaique.domain.models

data class Dialect(
    val slug: String,
    val dialect: String,
    val meanings: List<String>,
    val examples: List<String>
)

