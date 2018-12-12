/*package de.htwg.se.ninja.aview

import de.htwg.se.ninja.model.{Desk, Field, Player}

class Tui {
  def input(input: String, desk: Desk): Desk = {
    input match{
      case "n" => desk.setNewGame()
      case "f" => setFlag()
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
        case row :: colum :: Nil =>
      }
    }
  }
}


walk(player1, ninja, direction)