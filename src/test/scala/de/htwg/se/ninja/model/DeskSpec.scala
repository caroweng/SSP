package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class DeskSpec extends WordSpec with Matchers{
    "A desk" when {
        val player1: Player = Player("helen", StateOfPlayer.go)
        val player2: Player =  Player("caro", StateOfPlayer.pause)
        val field: Field = Field(Array.ofDim[Cell](6,6))
        val desk : Desk = Desk(field ,player1,player2)

        "be constructed with" in{
            desk.player1 should be (player1)
            desk.player2 should be (player2)
            desk.field should be (field)
        }


    }
}
