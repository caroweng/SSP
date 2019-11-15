package de.htwg.se.ninja.view

import de.htwg.se.ninja.controller.Controller
import de.htwg.se.ninja.util.Observer
import de.htwg.se.ninja.controller.State
import de.htwg.se.ninja.model.Weapon

import scala.util.matching.Regex

class Tui(controller: Controller) extends Observer {

    val FlagRegex: Regex = "f \\d \\d".r
    val WalkRegex: Regex = "w \\d \\d up|w \\d \\d down|w \\d \\d right|w \\d \\d left".r
    controller.add(this)

    def flagInput(input: String): String = {
        input match {
            case FlagRegex() => controller.setFlag(input.split(" ")(1).toInt, input.split(" ")(2).toInt)
            case _ => "Eingabe war nicht korrekt, bitte in der Form \"f Zahl Zahl\" eingeben"
        }
    }

    def turnInput(input: String): String = {
        input match {
            case WalkRegex() => controller.wonOrTurn(input)
            case _ => "Eingabe war nicht korrekt, bitte in der Form \"w Zahl Zahl Richtung\" eingeben"
        }
    }

    def input(input: String): Unit = {
        var output: String = ""
        controller.state match {
            case State.SET_FLAG1 => output = flagInput(input)
            case State.SET_FLAG2 => output = flagInput(input)
            case State.TURN => output = turnInput(input)
            case State.WON =>
                output = controller.currentPlayer.name +" hat gewonnen!!"
                System.exit(0)
        }
        printOutput(output)
    }

    def printOutput(output: String): Unit = {
        println(output)
    }

    override def update: String = {
        var output: String = ""
        controller.state match {
            case State.DIRECTION_DOES_NOT_EXIST => output = "In diese Richtung kannst du nicht laufen!"
            case State.TURN => output = controller.deskToString
            case State.SET_FLAG1 => output = controller.deskToString
            case State.SET_FLAG2 => output = controller.deskToString
            case State.No_NINJA_OR_NOT_VALID => output = "Wähle einen anderen Ninja der dir gehört!"
            case _ => output = "Unexpected Error occured!"
        }
        printOutput(output)
        output
    }
}
