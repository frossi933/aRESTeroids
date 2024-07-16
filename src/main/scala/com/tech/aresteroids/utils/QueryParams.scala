package com.tech.aresteroids.utils

import java.time.LocalDate
import org.http4s.QueryParamDecoder
import org.http4s.dsl.impl.OptionalQueryParamDecoderMatcher
import java.time.format.DateTimeFormatter

object QueryParams {

  implicit val localDateQueryParamDecoder: QueryParamDecoder[LocalDate] =
    QueryParamDecoder.localDateQueryParamDecoder(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

  object OptStartDateQueryParamMatcher  extends OptionalQueryParamDecoderMatcher[LocalDate]("startDate")
  object OptEndDateQueryParamMatcher    extends OptionalQueryParamDecoderMatcher[LocalDate]("endDate")
  object OptSortByNameQueryParamMatcher extends OptionalQueryParamDecoderMatcher[String]("sortByName")
}
