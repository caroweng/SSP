package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class FieldSpec extends WordSpec with Matchers{
  val playerC = Player("helen", Turn.pause)
  val playerH = Player("caro", Turn.go)
  val field = Field(Array.ofDim[Cell](3,3))
  val desk = Desk(field, playerC, playerH)
  desk.setNewGame()
  val n1 = desk.field.matrix(0)(2).ninja.get
  val n2 = desk.field.matrix(2)(2).ninja.get

  val m = desk.copy(field = desk.field.-(n1, desk.field.getPosition(n1)))
  m.field.matrix(0,2) should be (Cell(None))
  m.field.matrix(1,2) should be (Cell(None))
  val m2 = m.copy(field = m.field.+(n1, (1,2)))
  m2.field.matrix(1,2) should be (Cell(Some(n1)))
  m2.field.matrix(2,2) should be (Cell(Some(n2)))
  val d2 = m2.copy(field = m2.field.move(n2, Direction.up))
  //n1.weapon should be (Weapon.paper)
  //n2.weapon should be (Weapon.paper)
  //d2.field.matrix(1,2).ninja should be (Some(n1))


}
