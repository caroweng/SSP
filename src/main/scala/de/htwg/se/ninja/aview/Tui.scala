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
      case "f" =>
        input.split(" ").toList match {
          case f :: row :: col :: Nil  =>
            if(anzFlag < 2 && state == 0) {
              try {
                controller.setFlag(row, col)
                anzFlag += 1
              } catch{
                case iA: IllegalArgumentException => print("Du hast leider keinen ninja getroffen")
              }
              if (anzFlag == 2) state = 1
            } else {
              print("Sie haben ihr Flaggenpensum für heute aufgebraucht")
            }
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
      case _ =>
    }
  }

  override def update: Unit = println(controller.deskToString)
}
