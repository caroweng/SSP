package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

import scala.None

class FieldSpec extends WordSpec with Matchers{
  "A Field" should {
      "be constructed with a matrix" in {
        val field = Field(Array.ofDim[Cell](1,1))
        field.matrix(0)(0) should be (None)
      }

  }
}
