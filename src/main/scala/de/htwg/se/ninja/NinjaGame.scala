package de.htwg.se.ninja

import de.htwg.se.ninja.controller.component.{Controller, UpdateEvent}
import de.htwg.se.ninja.model.component.Desk
import de.htwg.se.ninja.model.component.component.component.component.{Cell, Field, Player, StateOfPlayer}
import de.htwg.se.ninja.view.{Gui, Tui}

import scala.io.StdIn.readLine

object NinjaGame {

  var player1 = Player("Spieler1", StateOfPlayer.go, 1)
  var player2 = Player("Spieler2", StateOfPlayer.pause, 2)
  var field = Field(Array.ofDim[Cell](6,6))

  val controller = new Controller(Desk(field, player1, player2).setNewGame())

  val tui = new Tui(controller)
  val gui = new Gui(controller)
  controller.publish(new UpdateEvent)

  def main(args: Array[String]): Unit= {
    var input: String = ""
    do {
      input = readLine()
      tui.input(input)
    } while (input != "q")
  }

}
