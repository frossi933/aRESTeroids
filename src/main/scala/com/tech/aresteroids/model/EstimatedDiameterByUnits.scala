package com.tech.aresteroids.model

import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec
final case class EstimatedDiameterByUnits(
    kilometers: EstimatedDiameter,
    meters: EstimatedDiameter,
    miles: EstimatedDiameter,
    feet: EstimatedDiameter
)
