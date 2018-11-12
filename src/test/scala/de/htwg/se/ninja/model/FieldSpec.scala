package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class FieldSpec extends WordSpec with Matchers{
  "A Field" should {
      "be constructed with a matrix" in {
        val field = Field(Array.ofDim[Cell](3,2), 3, 2)
        field.matrix(1)(0) should be (None)
      }

  }
}
