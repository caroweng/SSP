package de.htwg.se.ninja.view

import de.htwg.se.ninja.controller.Controller
import de.htwg.se.ninja.util.Observer
import de.htwg.se.ninja.controller.State
import de.htwg.se.ninja.model.{Cell, Ninja, Player, Weapon}

import scala.util.matching.Regex

class Tui(controller: Controller) extends Observer {

    val NameRegex: Regex = "name [A-Za-z]+".r
    val FlagRegex: Regex = "f \\d \\d".r
    val WalkRegex: Regex = "w \\d \\d up|w \\d \\d down|w \\d \\d right|w \\d \\d left".r
    val UndoRegex: Regex = "u".r
    val RedoRegex: Regex = "r".r
    val NextPlayerRegex: Regex = "y".r
    controller.add(this)

    def nameInput(input: String): State.state = {
        input match {
            case NameRegex() => controller.setName(input.replace("name ", ""))
            case _ => if(controller.currentPlayer.id == 1)
                controller.switchState(State.NAME_REGEX_INCORRECT_1)
            else
                controller.switchState(State.NAME_REGEX_INCORRECT_2)
        }
    }

    def flagInput(input: String): State.state = {
        input match {
            case FlagRegex() => controller.setFlag(input.split(" ")(1).toInt, input.split(" ")(2).toInt)
            case _ =>
                if(controller.currentPlayer.id == 1)
                    controller.switchState(State.FLAG_REGEX_INCORRECT_1)
                else
                    controller.switchState(State.FLAG_REGEX_INCORRECT_2)
        }
    }

    def turnInput(input: String): State.state = {
        input match {
            case WalkRegex() => controller.wonOrTurn(input)
            case RedoRegex() => controller.redo
            case _ => controller.switchState(State.WALK_REGEX_INCORRECT)
        }
    }

    def walkedInput(input: String): Unit = {
        input match {
            case UndoRegex() => controller.undo
            case NextPlayerRegex() => {
                controller.desk = controller.desk.changeTurns()
                controller.switchState(State.TURN)
            }
            case _ => controller.switchState(State.WALKED_INPUT_INCORRECT)
        }
    }

    def input(input: String): Unit = {
        controller.state match {
            case State.INSERTING_NAME_1 => nameInput(input)
            case State.INSERTING_NAME_2 => nameInput(input)
            case State.SET_FLAG_1 => flagInput(input)
            case State.SET_FLAG_2 => flagInput(input)
            case State.TURN => turnInput(input)
            case State.WALKED => walkedInput(input)
            case _ => println("unexpeced error")
        }
    }

    def deskToString: String = {
        val rows: Int= controller.desk.field.matrix.length
        val lineseparator: String = "  +" + ("----+") * rows + "\n"
        val line: String = (" |" + "   " )*rows + " |\n"
        var box: String = "\nrow 0  | 1  | 2  | 3  | 4  | 5" + "\n" + ( lineseparator + "n" +line ) * rows + lineseparator

        for (i <- controller.desk.field.matrix.indices) {
            box = box.replaceFirst("n", i.toString)
            for (j <- controller.desk.field.matrix.indices)
                box = box.replaceFirst("    ", this.toString(controller.currentPlayer, i, j))
        }
        box
    }

    def toString(curPlayer: Player, row: Int, col: Int): String ={
        var str: String = ""
        val cell: Cell = controller.desk.field.getCellAtPosition(row, col)
        if(cell.optNinja.isEmpty) {
            str = "[  ]"
            str
        } else {
            val ninja: Ninja = cell.getNinja()
            if(ninja.playerId == curPlayer.id) {
                if (ninja.playerId == controller.currentPlayer.id) str = " 1" else str = " 2"
                ninja.weapon match {
                    case Weapon.flag => str.concat("f ")
                    case Weapon.rock => str.concat("r ")
                    case Weapon.paper => str.concat("p ")
                    case Weapon.scissors => str.concat("s ")
                }
            } else {
                str = " xx "
                str
            }
        }
    }

    def stateToString(): String = {
        controller.state match {
            case State.INSERTING_NAME_1 => "Player 1 insert your name! name <name>"
            case State.INSERTING_NAME_2 => "Player 2 insert your name! name <name>"
            case State.SET_FLAG_1 => controller.currentPlayer.name + " set your flag! f <row> <col>" + deskToString
            case State.SET_FLAG_1_FAILED => "Flag could not be set, try again!"
            case State.SET_FLAG_2 => controller.currentPlayer.name + " set your flag! f <row> <col>" + deskToString
            case State.SET_FLAG_2_FAILED => "Flag could not be set, try again!"
            case State.NAME_REGEX_INCORRECT_1 => "Eingabe war nicht korrekt, bitte in der Form \"name [Name]\" eingeben"
            case State.NAME_REGEX_INCORRECT_2 => "Eingabe war nicht korrekt, bitte in der Form \"name [Name]\" eingeben"
            case State.WALK_REGEX_INCORRECT => "Eingabe war nicht korrekt, bitte in der Form \"w Zahl Zahl Richtung\" eingeben"
            case State.WALKED_INPUT_INCORRECT => "Eingabe war nicht korrekt, bitte <y> für nächsten Spieler oder <u> um Zug rückgängig machen eingeben"
            case State.FLAG_REGEX_INCORRECT_1 => "Eingabe war nicht korrekt, bitte in der Form \"f Zahl Zahl\" eingeben"
            case State.FLAG_REGEX_INCORRECT_2 => "Eingabe war nicht korrekt, bitte in der Form \"f Zahl Zahl\" eingeben"
            case State.No_NINJA_OR_NOT_VALID => "Not valid, try again!"
            case State.DIRECTION_DOES_NOT_EXIST => "Direction not valid, try again"
            case State.TURN => controller.currentPlayer.name + " it's your turn! w <row> <col> <up|down|left|right>" + deskToString
            case State.WALKED => controller.currentPlayer.name + " press <y> for next player or <u> for undo."+ deskToString
            case State.WON => controller.currentPlayer.name + " you win!"
        }
    }

    def printState(): Unit = {
        println(stateToString())
    }

    override def update: Unit = {
        controller.state match {
            case State.INSERTING_NAME_1 =>
            case State.INSERTING_NAME_2 =>
            case State.SET_FLAG_1 => deskToString
            case State.SET_FLAG_2 => deskToString
            case State.SET_FLAG_1_FAILED =>
                printState()
                controller.switchState(State.SET_FLAG_1)
                return
            case State.SET_FLAG_2_FAILED =>
                printState()
                controller.switchState(State.SET_FLAG_2)
                return
            case State.NAME_REGEX_INCORRECT_1 =>
                printState()
                controller.switchState(State.INSERTING_NAME_1)
                return
            case State.NAME_REGEX_INCORRECT_2 =>
                printState()
                controller.switchState(State.INSERTING_NAME_2)
                return
            case State.WALK_REGEX_INCORRECT =>
                printState()
                controller.switchState(State.TURN)
                return
            case State.FLAG_REGEX_INCORRECT_1 =>
                printState()
                controller.switchState(State.SET_FLAG_1)
                return
            case State.FLAG_REGEX_INCORRECT_2 =>
                printState()
                controller.switchState(State.SET_FLAG_2)
                return
            case State.WALKED_INPUT_INCORRECT =>
                printState()
                controller.switchState(State.WALKED)
                return
            case State.DIRECTION_DOES_NOT_EXIST =>
                printState()
                controller.switchState(State.TURN)
                return
//            case State.TURN => deskToString
            case State.TURN =>
            case State.WALKED =>
            case State.No_NINJA_OR_NOT_VALID =>
                printState()
                controller.switchState(State.TURN)
                return
            case State.WON =>
                printState()
                System.exit(0)
        }
        printState()
    }
}
