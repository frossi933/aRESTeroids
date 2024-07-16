package com.tech.aresteroids.repositories

import com.tech.aresteroids.model.Asteroid
import io.circe.Json
import org.http4s.client.Client
import io.circe.generic.auto._
import org.http4s.circe._
import cats.effect.kernel.Async
import cats.implicits._
import com.tech.aresteroids.model.DetailedAsteroid
import java.time.LocalDate
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.syntax._

trait AsteroidRepo[F[_]] {

  def getByDates(
      startDate: LocalDate,
      endDate: LocalDate
  ): F[Vector[Asteroid]]

  def get(id: String): F[Option[DetailedAsteroid]]
}

object AsteroidRepo {

  private val API_KEY = "DEMO_KEY"

  def apply[F[_]](implicit ev: AsteroidRepo[F]): AsteroidRepo[F] =
    ev

  def impl[F[_]: Async: Logger](client: Client[F]) =
    new AsteroidRepo[F] {

      def getByDates(
          startDate: LocalDate,
          endDate: LocalDate
      ): F[Vector[Asteroid]] =
        info"Fetching asteroids between $startDate and $endDate via https://api.nasa.gov" *>
          client
            .expect[Json](
              s"https://api.nasa.gov/neo/rest/v1/feed?start_date=$startDate&end_date=$endDate&api_key=$API_KEY"
            )
            .map { response =>
              val asteroidsObj = response.hcursor
                .downField("near_earth_objects")
              val dates = asteroidsObj.keys
                .getOrElse(Vector.empty)

              dates.foldLeft(Vector.empty[Asteroid]) { case (acc, date) =>
                acc ++ asteroidsObj
                  .get[Vector[Asteroid]](date)
                  .fold(
                    error => throw new RuntimeException(error.toString()),
                    asteroids => asteroids
                  )
              }
            }

      def get(id: String): F[Option[DetailedAsteroid]] =
        info"Fetching asteroid with id $id via https//api.nasa.gov" *>
          client
            .expect[Json](
              s"https://api.nasa.gov/neo/rest/v1/neo/$id?api_key=$API_KEY"
            )
            .map { response =>
              response
                .as[DetailedAsteroid]
                .fold(
                  error => throw new RuntimeException(error.toString()),
                  asteroid => Option(asteroid)
                )
            }
            .handleErrorWith(error =>
              error"Error fetching asteroid with id $id: ${error.getMessage()}" *>
                Async[F].pure(None)
            )

    }
}
