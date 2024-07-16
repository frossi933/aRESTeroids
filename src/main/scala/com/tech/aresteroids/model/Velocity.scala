package com.tech.aresteroids.model

import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec
final case class Velocity(
    kilometersPerSecond: String,
    kilometersPerHour: String,
    milesPerHour: String
)
