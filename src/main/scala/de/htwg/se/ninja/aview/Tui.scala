package de.htwg.se.ninja.aview

import de.htwg.se.ninja.controller.Controller
import de.htwg.se.ninja.util.Observer
import de.htwg.se.ninja.controller.State
import de.htwg.se.ninja.model.Weapon

import scala.util.matching.Regex

class Tui(controller: Controller) extends Observer {

  val FlagRegex: Regex = "f \\d \\d".r
  val WalkRegex: Regex = "w \\d \\d up|w \\d \\d down|w \\d \\d right|w \\d \\d left".r
  controller.add(this)

  def flagInput(input: String): Unit = {
    input match {
      case FlagRegex() => controller.setFlag(input.split(" ")(1).toInt, input.split(" ")(2).toInt)
      case _ => print("Eingabe war nicht korrekt, bitte in der Form \"f Zahl Zahl\" eingeben")
    }
  }

  def turnInput(input: String): Unit = {
    input match {
    case WalkRegex() => controller.wonOrTurn(input)
    case _ => print("Eingabe war nicht korrekt, bitte in der Form \"w Zahl Zahl Richtung\" eingeben")
    }
  }

  def input(input: String): Unit = {
    controller.state match {
      case State.SET_FLAG1 => flagInput(input)
      case State.SET_FLAG2 => flagInput(input)
      case State.TURN => turnInput(input)
      case State.WON =>
        print(controller.currentPlayer.name +" hat gewonnen!!")
        System.exit(0)
    }
  }

  override def update: Unit = {
    controller.state match {
      case State.DIRECTION_DOES_NOT_EXIST => print("In diese Richtung kannst du nicht laufen!")
      case State.TURN => println(controller.currentPlayer.name+": jetzt kannt du laufen!")
                         println(controller.deskToString)
      case State.SET_FLAG1 => print("Spieler 1: setz deine Flagge")
                              println(controller.deskToString)
      case State.SET_FLAG2 => print("Spieler 2: setz deine Flagge")
                              println(controller.deskToString)
      case  State.No_NINJA_OR_NOT_VALID => print("Wähle einen anderen Ninja der dir gehört!")
      case _ =>
    }
  }


}
