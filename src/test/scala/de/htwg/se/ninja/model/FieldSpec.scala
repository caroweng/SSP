package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class FieldSpec extends WordSpec with Matchers{
  "A Field" should {
      "be constructed with a matrix" in {
        val field = new Field(3,2)
        field(1)(0) should be (None)
        /*field.matrix(0)(0) should not be (Cell(None))
        val n = field.matrix(0)(0)
        n should have ('Team(Team.T1))*/
    }
  }
}
