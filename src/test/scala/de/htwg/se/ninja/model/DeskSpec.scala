package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class DeskSpec extends WordSpec with Matchers{
  val playerC = Player("helen", Turn.pause)
  val playerH = Player("caro", Turn.go)

  val desk = Desk(Field(Array.ofDim[Cell](3,3)), playerC, playerH)
  desk.player1.name should be("helen")
  desk.player2.state should be(Turn.go)
  desk.setNewGame()
  desk.field.matrix(1)(1) should be(Cell(None))
  desk.field.matrix(0)(2).ninja.get.id  should be(2)
  val n1 = desk.field.matrix(0)(2).ninja.get
  desk.walk(playerC, n1, Direction.down)
  desk.field.matrix(1)(2) should be (desk.field.matrix(0)(2).ninja.get)
}
