package com.tech.aresteroids.model

import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec
final case class EstimatedDiameter(
    estimatedDiameterMin: Double,
    estimatedDiameterMax: Double
)
