package de.htwg.se.ninja.controller

import de.htwg.se.ninja.model.{Desk, Direction, Weapon}
import de.htwg.se.ninja.util.Command

class WalkCommand (row: Int, col: Int, d: Direction.direction, controller: Controller) extends Command {
    val oldDesk: Desk = controller.desk.copy()
    override def doStep: State.state = {
        val ninja = controller.desk.field.getCellAtPosition(row, col)
        if (!ninja.exists()|| ninja.getNinja().weapon == Weapon.flag || ninja.getNinja().playerId != controller.currentPlayer.id) {
            return controller.switchState(State.No_NINJA_OR_NOT_VALID)
        }

        if (controller.desk.field.cellExists(row, col, d)) {
            controller.desk = controller.desk.copy(field = controller.desk.field.checkWalk(controller.desk.field.getCellAtPosition(row, col).getNinja(), d))

            println("Finished? Press <y> for next player")
            controller.switchState(State.WALKED)
        } else {
            controller.switchState(State.DIRECTION_DOES_NOT_EXIST)
        }
    }

    override def undoStep: State.state = {
        controller.desk = oldDesk;
        controller.switchState(State.TURN)
    }

    override def redoStep: State.state = {
        val ninja = controller.desk.field.getCellAtPosition(row, col)
        if (!ninja.exists()|| ninja.getNinja().weapon == Weapon.flag || ninja.getNinja().playerId != controller.currentPlayer.id) {
            return controller.switchState(State.No_NINJA_OR_NOT_VALID)
        }

        if (controller.desk.field.cellExists(row, col, d)) {
            controller.desk = controller.desk.copy(field = controller.desk.field.checkWalk(controller.desk.field.getCellAtPosition(row, col).getNinja(), d))
            controller.switchState(State.WALKED)
        } else {
            controller.switchState(State.DIRECTION_DOES_NOT_EXIST)
        }
    }


}
