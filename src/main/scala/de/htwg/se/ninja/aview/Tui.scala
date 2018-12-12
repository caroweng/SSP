package de.htwg.se.ninja.aview

import de.htwg.se.ninja.model.{Desk, Field, Player}

class Tui {

    def input(input: String, desk: Desk): Desk = {
      input match{
        case "q" => desk
        case "n" => desk.setNewGame()
        case _ =>
          input.split(" ").toList match {
            case player :: row :: col :: direction :: Nil  => desk.walk(desk.getPlayerWithName(player), desk.field.matrix(row.toInt, col.toInt).ninja.get, desk.toDirection(direction))
          }


        /* case "p"

        case "f" => setFlag()



          player, row, col, down
*/

      }
    }
}
