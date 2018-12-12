package de.htwg.se.ninja

import de.htwg.se.ninja.model._
import de.htwg.se.ninja.aview.Tui

import scala.io.StdIn.readLine

object Ninjatui {

  var player1 = Player("name", Turn.go)
  var player2 = Player("name2",Turn.pause)
  var field = Field(Array.ofDim[Cell](6,6))
  var desk = Desk(field,player1,player2).setNewGame()
  val tui = new Tui

  def main(args: Array[String]): Unit= {
    var input: String = ""
    do {
      println("Desk: " + desk.toString)
      input = readLine()
      desk = tui.input(input, desk)
    } while (input != "q")
  }

}
