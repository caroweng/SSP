package de.htwg.se.ninja

import de.htwg.se.ninja.model._
import de.htwg.se.ninja.aview.Tui
import de.htwg.se.ninja.controller.Controller

import scala.io.StdIn.readLine

object Ninjatui {

  var player1 = Player("Spieler1", Turn.go, false)
  var player2 = Player("Spieler2", Turn.pause, false)
  var field = Field(Array.ofDim[Cell](6,6))

  val controller = new Controller(Desk(field, player1, player2).setNewGame())

  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit= {
    var input: String = ""
    do {
      input = readLine()
      tui.input(input)
    } while (input != "q")
  }

}
