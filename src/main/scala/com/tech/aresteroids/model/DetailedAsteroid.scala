package com.tech.aresteroids.model

import io.circe.Decoder
import io.circe.HCursor
import io.circe.Json
import io.circe.syntax._
import io.circe.Encoder

final case class DetailedAsteroid(asteroid: Asteroid, details: AsteroidDetails)
object DetailedAsteroid {
  implicit val DetailedAsteroidEncoder: Encoder[DetailedAsteroid] =
    new Encoder[DetailedAsteroid] {
      final def apply(a: DetailedAsteroid): Json =
        a.asteroid.asJson.deepMerge(a.details.asJson)
    }
  implicit val DetailedAsteroidDecoder: Decoder[DetailedAsteroid] =
    new Decoder[DetailedAsteroid] {
      final def apply(c: HCursor): Decoder.Result[DetailedAsteroid] =
        for {
          asteroid <- c.as[Asteroid]
          details  <- c.as[AsteroidDetails]
        } yield new DetailedAsteroid(asteroid, details)
    }

}
