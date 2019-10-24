package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class DeskSpec extends WordSpec with Matchers{
    "A desk" when {
        val player1: Player = Player("helen", StateOfPlayer.go, 1)
        val player2: Player =  Player("caro", StateOfPlayer.pause, 1)
        val field: Field = Field(Array.ofDim[Cell](6,6))
        val desk : Desk = Desk(field, player1, player2)
        val desk5 = Desk

        "be a desk" in {
            desk5 should be(Desk)
        }

        "be constructed with" in{
            desk.player1 should be (Player("helen", StateOfPlayer.go, 1))
            desk.player2 should be (Player("caro", StateOfPlayer.pause, 1))
        }

        "set new game" in {
            val newDesk = desk.setNewGame()
//            newDesk.field should be(Field)
        }

        "change turns" in {
            val newDesk = desk.changeTurns()
            newDesk.player1.state should be(desk.player2.state)
        }

    }
}
