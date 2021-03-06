package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class DeskSpec extends WordSpec with Matchers{
  "A desk" when {
    val playerC = Player("helen", StateOfPlayer.pause)
    val playerH = Player("caro", StateOfPlayer.go)
    val desk = Desk(Field(Array.ofDim[Cell](3, 3)), playerC, playerH)
    "new game set" should {
      desk.setNewGame()
      "have Cells without ninjas" in {
        desk.field.matrix(1)(1) should be(Cell(None))
      }
      "have a ninja" in {
        val n1 = desk.field.matrix(0)(2).optNinja.get
        desk.field.matrix(0,2) should be (Cell(Some(n1)))
      }
    }
    "position of a ninja" should {
      val n1 = desk.field.matrix(0)(2).optNinja.get
      val pos = desk.field.getPosition(n1)
      "ninja be at position" in {
        pos._1 should be(0)
        pos._2 should be(2)
        desk.field.getPosition(n1) should be((0, 2))
      }
    }
    "its field" should {
      val n1 = desk.field.matrix(0)(2).optNinja.get
      val pos = desk.field.getPosition(n1)
      val add = desk.field.add((0,1), pos)
      val inbo = desk.field.inBounds(add)
      "cell in field exists" in {
        add._2 should be(3)
        inbo should be(false)
        desk.field.exists(pos._1, pos._2,  Direction.right) should be(false)
      }
    }
    "a ninja walks" should {
      val n1 = desk.field.matrix(0)(2).optNinja.get
      "change its position" in {
        val newDesk = desk.walk(playerC, n1, Direction.down)
        newDesk.field.matrix(0,2).optNinja should be(None)
        newDesk.field.matrix(1,2).optNinja should be(Some(n1))
      }
    }

    " a ninja " should{
      val ddd: Desk = desk.setFlag(playerC, 0, 0)
      "with flag" in {
        ddd.field.matrix(0,0).optNinja.get.weapon should be (Weapon.flag)
      }
    }

    /*"a ninja" should{
      val n3 = desk.field.matrix(2)(1).optNinja.get
      val d4 = desk.setFlag(playerH, 2,0 )
      "wins" in {
        d4.field.matrix(2)(1).optNinja should be(Some(n3))
        d4.win(d4.field.getPosition(n3)_1 , d4.field.getPosition(n3)_2, Direction.left) should be (true)
      }
    }*/

    "a Player" should{
      val einDesk = desk.changeTurns()
      "have a turn" in {
        desk.player1.state should be (StateOfPlayer.pause)
        desk.player2.state should be (StateOfPlayer.go)
      }
      "change turn" in {
        einDesk.player1.state should be(StateOfPlayer.go)
        einDesk.player2.state should be(StateOfPlayer.pause)
        print(desk.toString())
        print(einDesk.toString())
      }
    }
//    "a Player" should {
//      val name = "helen"
//      "be identified by its name" in {
//        desk.getPlayerWithName(name) should be(desk.player1)
//      }
//    }
//    "a String" should {
//      val s = "down"
//      "become a direction" in {
//        desk.toDirection(s) should be (Direction.down)
//      }
//    }
  }
}
