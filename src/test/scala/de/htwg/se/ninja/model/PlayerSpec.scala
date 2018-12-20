package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers{
  "A Player" when {

    val player1 = Player("caro", Turn.go)
    val player2 = Player("helen", Turn.pause)

    "be constructed with a name" in {
      player1.name should be("caro")
    }
    "a turn gets changed" should {
      val newPlayer1 = player1.changeTurn(player2.state)
      val newPlayer2 = player2.changeTurn(player1.state)
      "have a new turn" in {
        newPlayer1.state should be(Turn.pause)
        newPlayer2.state should be(Turn.go)
      }
    }
  }
}
