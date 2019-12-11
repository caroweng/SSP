package de.htwg.se.ninja.controller

import de.htwg.se.ninja.model._
import de.htwg.se.ninja.util.{Observable, UndoManager}

import scala.swing.Publisher
import scala.swing.event.Event

class Controller(var desk: Desk) extends Publisher {
    var state: State.state = State.INSERTING_NAME_1
    private val undoManager: UndoManager = new UndoManager();

    def newDesk(player1: Player, player2: Player, field: Field): Desk = {
        desk = Desk(field, player2, player1)
        publish(new UpdateEvent)
        desk
    }

    def newGame(): Desk = {
        desk = desk.setNewGame()
        publish(new UpdateEvent)
        desk
    }

    def currentPlayer: Player = if (desk.player1.state == StateOfPlayer.go) desk.player1 else desk.player2

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

    def walk(row: Int, col: Int, d: Direction.direction): State.state = {
        undoManager.doStep(new WalkCommand(row, col, d, this))
//        val ninja = desk.field.getCellAtPosition(row, col)
//        if (!ninja.exists()|| ninja.getNinja().weapon == Weapon.flag || ninja.getNinja().playerId != currentPlayer.id) {
//            return switchState(State.No_NINJA_OR_NOT_VALID)
//        }
//        if (desk.field.cellExists(row, col, d)) {
//            desk = desk.copy(field = desk.field.checkWalk(desk.field.getCellAtPosition(row, col).getNinja(), d))
//            desk = desk.changeTurns()
//            switchState(State.TURN)
//        } else {
//            switchState(State.DIRECTION_DOES_NOT_EXIST)
//        }
    }

    def changeTurns(): State.state = {
        desk = desk.changeTurns()
        switchState(State.TURN)
    }

    def switchState(newState: State.state): State.state = {
        state = newState
        println("switch state")
        publish(new UpdateEvent)
        state
    }

    def undo: State.state = {
        val state = undoManager.undoStep
        state
    }

    def redo: State.state = {
        val state = undoManager.redoStep
        state
    }
}

class UpdateEvent extends Event