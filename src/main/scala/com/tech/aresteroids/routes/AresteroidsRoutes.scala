package com.tech.aresteroids.routes

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import com.tech.aresteroids.services.Asteroids
import com.tech.aresteroids.utils.Sorting
import org.typelevel.log4cats.syntax._
import org.typelevel.log4cats.Logger
import com.tech.aresteroids.utils.QueryParams.OptStartDateQueryParamMatcher
import com.tech.aresteroids.utils.QueryParams.OptEndDateQueryParamMatcher
import com.tech.aresteroids.utils.QueryParams.OptSortByNameQueryParamMatcher

object AresteroidsRoutes {

  def asteroidsRoutes[F[_]: Sync: Logger](service: Asteroids[F]): HttpRoutes[F] = {

    val dsl = new Http4sDsl[F] {}
    import dsl._

    HttpRoutes.of[F] {

      case GET -> Root / "asteroids"
          :? OptStartDateQueryParamMatcher(maybeStartDate)
          :? OptEndDateQueryParamMatcher(maybeEndDate)
          :? OptSortByNameQueryParamMatcher(maybeSortByName) =>
        (for {
          _ <-
            info"Processing request to get asteroids between $maybeStartDate and $maybeEndDate, sorted by $maybeSortByName"
          asteroids <- service.getByDates(
            maybeStartDate,
            maybeEndDate,
            Sorting.from(maybeSortByName)
          )
          _    <- info"Found ${asteroids.size} asteroids"
          resp <- Ok(asteroids)
        } yield resp)
          .handleErrorWith {
            case Asteroids.StartDateAfterEndDateError(startDate, endDate) =>
              BadRequest(s"Start date ($startDate) cannot be after end date ($endDate)")
            case Asteroids.BothOrNeitherDatesRequiredError =>
              BadRequest("Both or neither start and end dates must be provided")
            case t: Throwable =>
              error"Internal Error fetching asteroids. ${t.getMessage()}" *> InternalServerError(
                "Service temporarily unavailable"
              )
          }

      case GET -> Root / "asteroids" / id =>
        info"Processing request to get asteroid with ID $id" *>
          service
            .getById(id)
            .flatMap {
              case Some(asteroid) => Ok(asteroid)
              case None           => NotFound()
            }
            .handleErrorWith {
              case Asteroids.EmptyAsteroidIdError =>
                BadRequest("Asteroid ID parameter cannot be empty")
              case t: Throwable =>
                error"Internal Error fetching asteroid. ${t.getMessage()}" *> InternalServerError(
                  "Service temporarily unavailable"
                )
            }
    }
  }
  /*
  def favouritesRoutes[F[_]: Sync](H: FavouritesRepo[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / "favourites" =>
      for {
        favs <- FavouritesRepo.getAll
        resp <- Ok(favs)
      } yield resp
    }
  }
   */
}
