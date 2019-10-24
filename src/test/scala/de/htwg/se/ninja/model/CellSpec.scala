package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class CellSpec extends WordSpec with Matchers{
    "A cell" when {
        val cell: Cell = Cell(None)
        val ninja: Ninja = Ninja(Weapon.paper, 1)
        val cell1: Cell = Cell(Some(ninja))
        val cell5 = Cell

        "be a cell" in {
            cell should be (Cell(None))
        }

        "be constructed without a Ninja" in {
            cell.optNinja should be (None)
            cell1.removeNinja().optNinja should be (None)
        }

        "be constructed with a Ninja" in {
            cell1.optNinja should be (Some(ninja))
        }

        "have an Ninja" in {
            cell1.exists() should be (true)
            cell1.getNinja() should be (ninja)
        }

        "not have an Ninja" in {
            cell.exists() should be (false)
        }
    }
}
