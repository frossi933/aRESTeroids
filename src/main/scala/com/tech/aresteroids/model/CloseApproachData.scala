package com.tech.aresteroids.model

import java.time.LocalDate
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec
final case class CloseApproachData(
    closeApproachDate: LocalDate,
    closeApproachDateFull: String,
    epochDateCloseApproach: Long,
    relativeVelocity: Velocity,
    missDistance: DistanceByUnits,
    orbitingBody: String
)
