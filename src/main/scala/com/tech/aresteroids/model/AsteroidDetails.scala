package com.tech.aresteroids.model

import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec
final case class AsteroidDetails(designation: String, orbitalData: OrbitalData)
