package de.htwg.se.ninja.controller

import java.util.NoSuchElementException

import de.htwg.se.ninja.model._
import de.htwg.se.ninja.util.Observable

class Controller(var desk: Desk) extends Observable {
  var state: State.state = State.SET_FLAG1

  def wonOrTurn(input: String) = {
    var dir: Direction.direction = null
    input.split(" ")(2) match {
      case "down" => dir = Direction.down
      case "up" => dir = Direction.up
      case "left" => dir = Direction.left
      case "right" => dir = Direction.right
    }
    val row = input.split(" ")(1).toInt
    val col = input.split(" ")(2).toInt

    if (desk.win(row, col, dir)) {
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

  def currentPlayer : Player = if (desk.player1.state == Turn.go) desk.player1 else desk.player2

  def setFlag(row: Int, col: Int): Unit = {
    if (state == State.SET_FLAG1) {
      if (desk.field.isNinjaOfPlayerAtPosition(desk.player1, row, col)) {
        desk = desk.setFlag(desk.player1, row, col)
        switchState(State.SET_FLAG2)
        return
      }
      switchState(State.No_NINJA_OR_NOT_YOUR)
      switchState(State.SET_FLAG1)
    } else if (state == State.SET_FLAG2) {
      if (desk.field.isNinjaOfPlayerAtPosition(desk.player2, row, col)) {
        desk = desk.setFlag(desk.player2, row, col)
        switchState(State.TURN)
        return
      }
      switchState(State.No_NINJA_OR_NOT_YOUR)
      switchState(State.SET_FLAG2)
    }

  }

  def deskToString: String = desk.toString

  def switchState(newState: State.state): Unit = {
    state = newState
    notifyObservers
  }


  def walk(row: Int, col: Int, d: Direction.direction): Unit = {
    if (!desk.field.matrix(row, col).exists()) {
      switchState(State.No_NINJA_OR_NOT_YOUR)
      switchState(State.TURN)
    }
    if (desk.field.exists(desk.field.matrix(row, col).getNinja(), d)) {
      desk = desk.walk(currentPlayer, desk.field.matrix(row, col).getNinja(), d)
      desk = desk.changeTurns()
      switchState(State.TURN)
    } else {
      switchState(State.DIRECTION_DOES_NOT_EXIST)
      switchState(State.TURN)
    }
    notifyObservers
  }



}