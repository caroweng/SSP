package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class FieldSpec extends WordSpec with Matchers {
    "A field" when {
        var matrix = Array.ofDim[Cell](3, 3)
        val ninja: Ninja = Ninja(Weapon.paper, 1)
        matrix(0)(0) = Cell(Some(ninja))
        val field = Field(matrix)
        val field5 = Field

        "be a field" in {
            field5 should be(Field)
        }

        "be constructed with " in {
            field.getCellAtPosition(0,0) should be(Cell(Some(ninja)))
        }

        "be set empty" in {
            val field2 = field.setEmpty()
            field2.getCellAtPosition(0,0) should be(Cell(None))
        }

        "be set with ninjas" in {
            val field2 = field.setInitialNinjaRows()
            field2.getCellAtPosition(0,0).getNinja().playerId should be(1)
            field2.getCellAtPosition(2,2).getNinja().playerId should be(2)
        }

        "be set empty with initial ninja rows" in {
            val field2 = field.setNewField()
            field2.getCellAtPosition(0,0).optNinja should not be(None)
        }


    }
}