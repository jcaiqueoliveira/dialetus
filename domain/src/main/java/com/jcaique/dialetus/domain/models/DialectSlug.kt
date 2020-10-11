package com.jcaique.dialetus.domain.models

import java.io.Serializable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
inline class DialectSlug(val slug: String) : Serializable
