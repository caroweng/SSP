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
  val n2 = desk.field.matrix(2,2).ninja.get
  desk.field.getPosition(n2) should be((2,2))

  val n1 = desk.field.matrix(0)(2).ninja.get

  val pos = desk.field.getPosition(n1)
  val add = desk.field.add((0,1), pos)
  val inbo = desk.field.inBounds(add)
  pos._1 should be (0)
  pos._2 should be (2)
  add._2 should be (3)
  inbo should be (false)
  desk.field.exists(n1, Direction.right) should be (false)

  desk.field.-(n1, (0,2)).matrix(0,2) should be (Cell(None))
  desk.field.matrix(1,2) should be (Cell(None))
  desk.field.+(n1,(1,2)).matrix(1,2) should be (Cell(Some(n1)))


  desk.field.matrix(pos) should be (Cell(Some(n1)))
  //desk.walk(playerC, n1, Direction.down)

  //desk.field.matrix(0,2) should be(Cell(None))
  //desk.field.matrix(0, 2).ninja.get should be (None)


  desk.field.add((0,1),(2,0)) should be((2,1))
  desk.field.inBounds((0,1)) should be(true)
  desk.field.exists(n1, Direction.down) should be (true)
}
