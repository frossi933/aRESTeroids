package com.tech.aresteroids.model

import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec
final case class DistanceByUnits(
    astronomical: String,
    lunar: String,
    kilometers: String,
    miles: String
)
