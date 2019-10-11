package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class FieldSpec extends WordSpec with Matchers {
  "A field" when {
    val player1 = Player("helen", StateOfPlayer.pause)
    val player2 = Player("caro", StateOfPlayer.go)
    val field = Field(Array.ofDim[Cell](3, 3))
    var desk = Desk(field, player1, player2)
    desk = desk.setNewGame()

    "a ninja gets removed" should {
      val n1 = desk.field.matrix(0)(2).optNinja.get
      val n2 = desk.field.matrix(2)(2).optNinja.get
      val m = desk.copy(field = desk.field.-(n1, desk.field.getPosition(n1)))
      "have one ninja less" in {
        m.field.matrix(0, 2) should be(Cell(None))
        m.field.matrix(1, 2) should be(Cell(None))
      }
    }
    "a ninja gets added" should {
      val n1 = desk.field.matrix(0)(2).optNinja.get
      val n2 = desk.field.matrix(2)(2).optNinja.get
      val m2 = desk.copy(field = field + (n1, (1, 2)))
      "have one more" in {
        m2.field.matrix(1, 2) should be(Cell(Some(n1)))
        m2.field.matrix(2, 2) should be(Cell(Some(n2)))
      }
    }
    "a ninja moves" should {
      val n1 = desk.field.matrix(0)(2).optNinja.get
      val n2 = desk.field.matrix(2)(2).optNinja.get
      val newField = desk.field.move(n1, Direction.down)
      val newField2 = newField.move(n2, Direction.up)
      "be at new posiotion" in {
        newField.matrix(1,2).optNinja should be(Some(n1))
        newField.matrix(0,2).optNinja should be(None)
      }
      "fight another ninja" in {
        newField2.matrix(0,2).optNinja should be(None)
        newField2.matrix(2,2).optNinja should be (None)
      }
    }
    "a ninja fights another ninja" should {
      val n1 = Ninja(Weapon.rock, player1)
      val n2 = Ninja(Weapon.scissors, player2)
      val n3 = Ninja(Weapon.scissors, player2)
      val winner = desk.field.fight(n1, n2)
      val winner2 = desk.field.fight(n2,n3)
      "ninja1 wins" in {
        winner should be(n1)
      }
    }
  }
}