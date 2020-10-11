package com.jcaique.dialetus.domain.models

import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.Serializable

@OptIn(ExperimentalCoroutinesApi::class)
inline class DialectSlug(val slug: String) : Serializable
