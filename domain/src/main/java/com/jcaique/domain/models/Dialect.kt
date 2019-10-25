package com.jcaique.domain.models

import java.io.Serializable

data class Dialect(
  val slug: String,
  val dialect: String,
  val meanings: List<String>,
  val examples: List<String>
) : Serializable
