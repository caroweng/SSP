package de.htwg.se.ninja.aview

import java.util.NoSuchElementException

import de.htwg.se.ninja.controller.Controller
import de.htwg.se.ninja.model.{Desk, Field, Player}
import de.htwg.se.ninja.util.Observer

class Tui (controller: Controller) extends Observer{

  controller.add(this)
  var state = 0
  var anzFlag = 0

  def input(input: String): Unit = {

    val in = input.split(" ").toList
    in.head match{
      case "q" =>
      case "n" => controller.newGame()
                  state = 0
                  anzFlag = 0
      case "f" =>
        input.split(" ").toList match {
          case f :: player :: row :: col :: Nil  =>
            if(anzFlag < 2 && state == 0) {
              tryFlag(player, row, col)
            } else {
              print("Sie haben ihr Flaggenpensum für heute aufgebraucht")
            }
          case _ => print("Nochmal bitte")
        }
      case "w" => {
        input.split(" ").toList match {
          case w :: player :: row :: col :: direction :: Nil =>
            if (controller.checkState(player) && state == 1) {
              val success = controller.win(row, col, direction)
              if (success) {
                println(player + "hat gewonnen!")
                controller.newGame()
              } else {
                try {
                  controller.walk(player, row, col, direction)
                  controller.changeTurn()
                } catch {
                  case no: NoSuchElementException => print("Feld existiert nicht")
                  case iS: IllegalStateException => print("Mit Flagge darf nicht gelaufen werden")
                  case iA: IllegalArgumentException => print("Eigentor")
                }
              }
            } else if (state == 0) print("setz Flagge")
            else print("anderer Spieler ist dran")
        }
      }
      case _ => print("Dat war wohl nichts... nochmal! ")
    }
  }

  def tryFlag(player: String, row: String, col: String): Unit = {
    try {
      controller.setFlag(player, row, col)
      anzFlag += 1
    } catch {
      case nS: NoSuchElementException => print("Du hast leider keinen ninja getroffen")
      case iA: IllegalArgumentException => print("Player hat schon eine Flagge")
      case iS: IllegalStateException => print("Ninja gehört dir nicht!")
        if (anzFlag == 2) state = 1
    }
  }

  override def update: Unit = println(controller.deskToString)
}
