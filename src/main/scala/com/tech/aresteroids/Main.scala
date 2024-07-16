package com.tech.aresteroids

import cats.effect.IO
import cats.effect.IOApp
import org.typelevel.log4cats.Logger
import cats.effect.kernel.Sync
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Main extends IOApp.Simple {

  implicit def logger[F[_]: Sync]: Logger[F] = Slf4jLogger.getLogger[F]

  val run = AresteroidsServer.run[IO]
}
