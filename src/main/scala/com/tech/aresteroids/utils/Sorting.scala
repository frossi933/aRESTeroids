package com.tech.aresteroids.utils

sealed trait Sorting extends Product with Serializable
case object NoSort   extends Sorting
case object Asc      extends Sorting
case object Desc     extends Sorting

object Sorting {

  def from(sort: Option[String]): Sorting = sort match {
    case Some(v) if "desc".equalsIgnoreCase(v) => Desc
    case Some(v) if "asc".equalsIgnoreCase(v)  => Asc
    case _                                     => NoSort
  }

}
