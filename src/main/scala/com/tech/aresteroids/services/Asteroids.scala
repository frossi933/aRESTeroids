package com.tech.aresteroids.services

import com.tech.aresteroids.model.Asteroid
import com.tech.aresteroids.repositories.AsteroidRepo
import com.tech.aresteroids.model.DetailedAsteroid
import java.time.LocalDate
import com.tech.aresteroids.utils.Sorting
import cats.implicits._
import com.tech.aresteroids.utils.Desc
import com.tech.aresteroids.utils.Asc
import com.tech.aresteroids.utils.NoSort
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.syntax._
import cats.effect.kernel.Async

trait Asteroids[F[_]] {

  def getByDates(
      maybeStartDate: Option[LocalDate],
      maybeEndDate: Option[LocalDate],
      sorting: Sorting
  ): F[Vector[Asteroid]]

  def getById(id: String): F[Option[DetailedAsteroid]]
}

object Asteroids {
  def apply[F[_]](implicit ev: Asteroids[F]): Asteroids[F] = ev

  sealed trait AsteroidError                                                      extends Exception
  case class StartDateAfterEndDateError(startDate: LocalDate, endDate: LocalDate) extends AsteroidError
  case object BothOrNeitherDatesRequiredError                                     extends AsteroidError
  case object EmptyAsteroidIdError                                                extends AsteroidError

  def impl[F[_]: Async: Logger](repo: AsteroidRepo[F]): Asteroids[F] =
    new Asteroids[F] {

      def getByDates(
          maybeStartDate: Option[LocalDate],
          maybeEndDate: Option[LocalDate],
          sorting: Sorting
      ): F[Vector[Asteroid]] = {
        val asteroidsF = (maybeStartDate, maybeEndDate) match {
          case (Some(startDate), Some(endDate)) =>
            if (startDate.isAfter(endDate)) {
              error"Start date ($startDate) cannot be after end date ($endDate)" *>
                Async[F].raiseError(StartDateAfterEndDateError(startDate, endDate))
            } else
              repo.getByDates(startDate, endDate)

          case (None, None) =>
            info"Fetching asteroids for today" *>
              Async[F].delay(LocalDate.now()).flatMap { today =>
                repo.getByDates(today, today)
              }

          case _ =>
            error"Both or neither start and end dates must be provided" *>
              Async[F].raiseError(BothOrNeitherDatesRequiredError)
        }

        asteroidsF.map { asteroids =>
          sorting match {
            case NoSort => asteroids
            case Asc    => asteroids.sortBy(_.name)
            case Desc   => asteroids.sortBy(_.name)
          }
        }
      }

      def getById(id: String): F[Option[DetailedAsteroid]] =
        if (id.trim().isEmpty()) {
          error"Asteroid id cannot be empty" *>
            Async[F].raiseError(EmptyAsteroidIdError)
        } else
          repo.get(id)
    }
}
