val CatsVersion            = "2.12.0"
val CatsEffectVersion      = "3.5.4"
val Http4sVersion          = "0.23.27"
val CirceVersion           = "0.14.9"
val CirceGenericExtras     = "0.14.3"
val MunitVersion           = "1.0.0"
val LogbackVersion         = "1.5.6"
val MunitCatsEffectVersion = "2.0.0"
val Log4CatsVersion        = "2.7.0"

lazy val root = (project in file("."))
  .settings(
    organization := "com.tech",
    name         := "aresteroids",
    version      := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.14",
    scalacOptions ++= Seq(
      "-Ymacro-annotations"
    ),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core"            % CatsVersion,
      "org.typelevel" %% "cats-effect"          % CatsEffectVersion,
      "org.http4s"    %% "http4s-ember-server"  % Http4sVersion,
      "org.http4s"    %% "http4s-ember-client"  % Http4sVersion,
      "org.http4s"    %% "http4s-circe"         % Http4sVersion,
      "org.http4s"    %% "http4s-dsl"           % Http4sVersion,
      "io.circe"      %% "circe-generic"        % CirceVersion,
      "io.circe"      %% "circe-generic-extras" % CirceGenericExtras,
      "io.circe"      %% "circe-parser"         % CirceVersion,
      "org.scalameta" %% "munit"                % MunitVersion           % Test,
      "org.typelevel" %% "munit-cats-effect"    % MunitCatsEffectVersion % Test,
      "ch.qos.logback" % "logback-classic"      % LogbackVersion         % Runtime,
      "org.typelevel" %% "log4cats-slf4j"       % Log4CatsVersion
    ),
    addCompilerPlugin(
      "org.typelevel" %% "kind-projector" % "0.13.3" cross CrossVersion.full
    ),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case x                   => (assembly / assemblyMergeStrategy).value.apply(x)
    }
  )
