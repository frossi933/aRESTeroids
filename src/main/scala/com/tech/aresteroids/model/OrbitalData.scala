package com.tech.aresteroids.model

import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec
final case class OrbitalData(
    orbitId: String,
    orbitDeterminationDate: String,
    firstObservationDate: String,
    lastObservationDate: String,
    dataArcInDays: Int,
    observationsUsed: Int,
    orbitUncertainty: String,
    minimumOrbitIntersection: String,
    jupiterTisserandInvariant: String,
    epochOsculation: String,
    eccentricity: String,
    semiMajorAxis: String,
    inclination: String,
    ascendingNodeLongitude: String,
    orbitalPeriod: String,
    perihelionDistance: String,
    perihelionArgument: String,
    aphelionDistance: String,
    perihelionTime: String,
    meanAnomaly: String,
    meanMotion: String,
    equinox: String,
    orbitClass: OrbitClass
)

@ConfiguredJsonCodec
final case class OrbitClass(
    orbitClassType: String,
    orbitClassDescription: String,
    orbitClassRange: String
)
