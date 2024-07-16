package com.tech.aresteroids

import io.circe.generic.extras.Configuration

package object model {

  implicit val config: Configuration =
    Configuration.default.withSnakeCaseMemberNames
}
