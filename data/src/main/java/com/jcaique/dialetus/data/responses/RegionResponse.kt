package com.jcaique.dialetus.data.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class RegionResponse(
  val name: String,
  val total: Int
)
