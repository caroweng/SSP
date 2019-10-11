package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class FieldSpec extends WordSpec with Matchers {
  "A field" when {
    val field = Field(Array.ofDim[Cell](3, 3))

    " be constructed with " in {
      field.getCellAtPosition(1,1) should be ()
    }

  }
}