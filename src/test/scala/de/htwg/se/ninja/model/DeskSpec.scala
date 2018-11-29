package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class DeskSpec extends WordSpec with Matchers{

  val fc = new FieldCreator(2,3)

  val field = fc.newGame()
  Desk(field, 0, 1, Direction.down) should be ()


}
