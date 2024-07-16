package com.tech.aresteroids.model

import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec
final case class Asteroid(
    id: String,
    neoReferenceId: String,
    name: String,
    nasaJplUrl: String,
    absoluteMagnitudeH: Float,
    estimatedDiameter: EstimatedDiameterByUnits,
    isPotentiallyHazardousAsteroid: Boolean,
    closeApproachData: Vector[CloseApproachData],
    isSentryObject: Boolean
)
