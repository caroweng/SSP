package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers{
  "A Player" should {
    "be constructed with a name and a turn" in {
      val player1 = Player("caro", Turn.go)
      val player2 = Player("helen", Turn.pause)
      player1.name should be("caro")
      player1.changeTurn(player2)
      player1.state should be(Turn.pause)
      player2.state should be(Turn.go)
    }
  }
}
