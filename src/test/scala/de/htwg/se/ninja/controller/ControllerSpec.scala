package de.htwg.se.ninja.controller

import de.htwg.se.ninja.controller.component.{Controller, State}
import de.htwg.se.ninja.model.component.Desk
import de.htwg.se.ninja.model.component.component.component.component._
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers{
    "A controller" when {
        val player1: Player = Player("helen", StateOfPlayer.go, 1)
        val player2: Player = Player("caro", StateOfPlayer.pause, 1)
        val field: Field = Field(Array.ofDim[Cell](6, 6))
        val desk: Desk = Desk(field, player1, player2).setNewGame()
        val controller = new Controller(desk)
        controller.newGame()

        "create a new Desk" in {
            val newPlayer: Player = Player("caro2", StateOfPlayer.pause, 1)
            val newDesk = controller.newDesk(player1, newPlayer, field)
            controller.desk should be(newDesk)
            desk should not be (newDesk)
        }

//        "set a players name" in {
//            controller.switchState(State.INSERTING_NAME_1)
//            controller.currentPlayer.name should be("helen")
//            controller.setName("newName")
//            controller.currentPlayer.name should be("newName")
//            controller.desk.changeTurns()
//            controller.switchState(State.INSERTING_NAME_2)
//            controller.currentPlayer.name should be("caro")
//            controller.setName("otherName")
//            controller.currentPlayer.name should be("otherName")
//        }

        "changeTurns" in {
            controller.newGame()
            controller.desk.player1.state should be(StateOfPlayer.pause)
            controller.desk.player2.state should be(StateOfPlayer.go)
            controller.desk = controller.desk.changeTurns()
            controller.desk.player1.state should be(StateOfPlayer.go)
            controller.desk.player2.state should be(StateOfPlayer.pause)
        }

        "set a flag" in {
            controller.newGame()
            controller.switchState(State.SET_FLAG_1)
            controller.setFlag(1,1)
            controller.desk.field.getCellAtPosition(0,0).getNinja().ninjaId should be (0)
        }

        "switch state" in {
            controller.switchState(State.SET_FLAG_2)
            controller.state should be (State.SET_FLAG_2)
        }

        "not be a valid ninja" in {
            controller.newGame()
            controller.walk(3,3, Direction.up) should be(State.No_NINJA_OR_NOT_VALID)
        }

        "not be a valid direction" in {
            controller.newGame()
            controller.walk(0,0, Direction.up) should be(State.DIRECTION_DOES_NOT_EXIST)
        }

        "walk a ninja" in {
            controller.newGame()
            controller.walk(1,1, Direction.down) should be(State.WALKED)
        }
    }
}
