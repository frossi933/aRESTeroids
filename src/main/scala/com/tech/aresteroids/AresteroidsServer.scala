package com.tech.aresteroids

import cats.effect.Async
//import cats.syntax.all._
import com.comcast.ip4s._
import fs2.io.net.Network
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.{ Logger => LoggerMiddleware }
import com.tech.aresteroids.services
import com.tech.aresteroids.repositories.AsteroidRepo
import org.typelevel.log4cats.Logger

object AresteroidsServer {

  def run[F[_]: Async: Network: Logger]: F[Nothing] = {
    for {
      client <- EmberClientBuilder.default[F].build
      repo         = AsteroidRepo.impl[F](client)
      asteroidsAlg = services.Asteroids.impl[F](repo)
      httpApp =
        routes.AresteroidsRoutes
          .asteroidsRoutes[F](asteroidsAlg) // <+> AresteroidsRoutes.favouritesRoutes[F](jokeAlg)
          .orNotFound
      finalHttpApp = LoggerMiddleware.httpApp(true, true)(httpApp)
      _ <-
        EmberServerBuilder
          .default[F]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever
}
