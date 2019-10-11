package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers{
    "A Player" when {

        val player1 = Player("caro", StateOfPlayer.go)
        val player2 = Player("helen", StateOfPlayer.pause)

        "be constructed with a name" in {
            player1.name should be("caro")
            player2.name should be("helen")
            player1.state should be(StateOfPlayer.go)
            player2.state should be(StateOfPlayer.pause)
        }

        "a turn gets changed" should {
            val newPlayer1 = player1.changeState(player2.state)
            val newPlayer2 = player2.changeState(player1.state)
            "have a new turn" in {
                newPlayer1.state should be(StateOfPlayer.pause)
                newPlayer2.state should be(StateOfPlayer.go)
            }
        }
    }
}
