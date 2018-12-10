package de.htwg.se.ninja.controller

import de.htwg.se.ninja.model._
import de.htwg.se.ninja.util.Observable

class Controller (var desk: Desk) extends Observable{

  def newDesk(player1: Player, player2: Player, field: Field): Unit = {
    desk = Desk(field, player2, player1)
    notifyObservers
  }

  def deskToString: String = desk.toString

  def walk(player: Player, ninja: Ninja, d: Direction.direction): Unit = {
    desk = desk.walk(player, ninja, d)
    notifyObservers
  }

}