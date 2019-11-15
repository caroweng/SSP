package de.htwg.se.ninja.controller

import de.htwg.se.ninja.model._
import de.htwg.se.ninja.util.Observable

class Controller(var desk: Desk) extends Observable {
    var state: State.state = State.INSERTING_NAME_1

    def wonOrTurn(input: String): State.state = {
        var dir: Direction.direction = null
        input.split(" ")(3) match {
            case "down" => dir = Direction.down
            case "up" => dir = Direction.up
            case "left" => dir = Direction.left
            case "right" => dir = Direction.right
        }
        val row: Int = input.split(" ")(1).toInt
        val col: Int = input.split(" ")(2).toInt

        if (desk.field.cellExists(row, col, dir) && desk.win(row, col, dir)) {
            switchState(State.WON)

        } else {
            walk(row, col, dir)
        }
    }

    def newDesk(player1: Player, player2: Player, field: Field): Unit = {
        desk = Desk(field, player2, player1)
        notifyObservers
    }

    def newGame(): Unit = {
        desk = desk.setNewGame()
        notifyObservers
    }

    def currentPlayer : Player = if (desk.player1.state == StateOfPlayer.go) desk.player1 else desk.player2

    def setFlag(row: Int, col: Int): State.state = {
        if (state == State.SET_FLAG_1) {
            if (desk.field.isNinjaOfPlayerAtPosition(desk.player1, row, col)) {
                desk = desk.copy(field = desk.field.setFlag(desk.player1.id, row, col))
                desk = desk.changeTurns()
                return switchState(State.SET_FLAG_2)
            }
            switchState(State.SET_FLAG_1_FAILED)
        } else {
            if (desk.field.isNinjaOfPlayerAtPosition(desk.player2, row, col)) {
                desk = desk.copy(field = desk.field.setFlag(desk.player2.id, row, col))
                desk = desk.changeTurns()
                return switchState(State.TURN)
            }
            switchState(State.SET_FLAG_2_FAILED)
        }
    }

    def setName(name: String): State.state = {
        if (state == State.INSERTING_NAME_1) {
            desk = desk.copy(player1 = currentPlayer.setName(name))
            desk = desk.changeTurns()
            switchState(State.INSERTING_NAME_2)
        } else {
            desk = desk.copy(player2 = currentPlayer.setName(name))
            desk = desk.changeTurns()
            switchState(State.SET_FLAG_1)
        }
    }

    def switchState(newState: State.state): State.state = {
        state = newState
        notifyObservers
        state
    }

    def walk(row: Int, col: Int, d: Direction.direction): State.state = {
        val ninja = desk.field.getCellAtPosition(row, col)
        if (!ninja.exists()|| ninja.getNinja().weapon == Weapon.flag || ninja.getNinja().playerId != currentPlayer.id) {
            return switchState(State.No_NINJA_OR_NOT_VALID)
        }
        if (desk.field.cellExists(row, col, d)) {
            desk = desk.copy(field = desk.field.checkWalk(desk.field.getCellAtPosition(row, col).getNinja(), d))
            desk = desk.changeTurns()
            switchState(State.TURN)
        } else {
            switchState(State.DIRECTION_DOES_NOT_EXIST)
        }
    }
}