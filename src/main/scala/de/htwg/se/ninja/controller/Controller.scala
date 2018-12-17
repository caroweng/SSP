package de.htwg.se.ninja.controller

import de.htwg.se.ninja.model._
import de.htwg.se.ninja.util.Observable

class Controller (var desk: Desk) extends Observable {

  def newDesk(player1: Player, player2: Player, field: Field): Unit = {
    desk = Desk(field, player2, player1)
    notifyObservers
  }

  def newGame(): Unit = {
    desk = desk.setNewGame()
    notifyObservers
  }

  def setFlag(player: String, row: String, col: String): Unit = {
    val ninja = desk.field.matrix(row.toInt, col.toInt).ninja.getOrElse(throw new NoSuchElementException)
    desk = desk.setFlag(desk.toPlayer(player), row.toInt, col.toInt)
    notifyObservers
  }

  def deskToString: String = desk.toString

  def walk(player: String, row:String, col: String, d: String): Unit = {
    desk = desk.walk(desk.getPlayerWithName(player), desk.field.matrix(row.toInt, col.toInt).ninja.get, desk.toDirection(d))
    notifyObservers
  }

  def win(row: String, col: String, d: String): Boolean = {
    val success = desk.win(row.toInt, col.toInt, desk.toDirection(d))
    notifyObservers
    success
  }

  def changeTurn(): Unit = {
    desk = desk.changeTurns()
  }

  def checkState(player: String): Boolean = {
    if(desk.getPlayerWithName(player).state == Turn.go) true else false
  }

}