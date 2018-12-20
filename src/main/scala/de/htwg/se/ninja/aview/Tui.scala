package de.htwg.se.ninja.aview

import java.util.NoSuchElementException

import de.htwg.se.ninja.controller.Controller
import de.htwg.se.ninja.model.{Desk, Direction, Field, Player}
import de.htwg.se.ninja.util.Observer
import de.htwg.se.ninja.controller.State
import de.htwg.se.ninja.model.Direction.direction

import scala.util.matching.Regex

class Tui(controller: Controller) extends Observer {

  val FlagRegex: Regex = "f \\d \\d".r
  val WalkRegex: Regex = "w \\d \\d (up|down|right|left)".r
  controller.add(this)


  def flagInput(input: String): Unit = {
    input match {
      case FlagRegex() => controller.setFlag(input.split(" ")(1).toInt, input.split(" ")(2).toInt)
      case _ => print("Eingabe war nicht korrekt bitte in der Form ??? eingeben")
    }
  }

  def turnInput(input: String): Unit = {
    input match {
    case WalkRegex() => controller.wonOrTurn(input)
    case _ => print("Eingabe war nicht korrekt bitte in der Form ??? eingeben")
    }
  }


  def input(input: String): Unit = {
    controller.state match {
      case State.SET_FLAG1 => flagInput(input)
      case State.SET_FLAG2 => flagInput(input)
      case State.TURN => turnInput(input)
      case State.WON =>
        print("Du hast gewonne. gluckwunsch. ciaio")
        System.exit(0)
    }
  }


  override def update: Unit = {
    controller.state match {
      case State.DIRECTION_DOES_NOT_EXIST => print()
      case State.TURN => print()
      case _ =>
    }
    println(controller.deskToString)
  }
}
