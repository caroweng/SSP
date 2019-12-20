package de.htwg.se.ninja.controller.component

import de.htwg.se.ninja.model.DeskInterface
import de.htwg.se.ninja.model.component.Desk
import de.htwg.se.ninja.model.component.component.component.component.{Direction, Weapon}
import de.htwg.se.ninja.util.Command

class WalkCommand (row: Int, col: Int, d: Direction.direction, controller: Controller) extends Command {
    val oldDesk: DeskInterface = controller.desk.copyDesk()
    override def doStep: State.state = {
        val ninja = controller.desk.field.getCellAtPosition(row, col)
        if (!ninja.exists()|| ninja.getNinja().weapon == Weapon.flag || ninja.getNinja().playerId != controller.currentPlayer.id) {
            return controller.switchState(State.No_NINJA_OR_NOT_VALID)
        }

        if (controller.desk.field.cellExists(row, col, d)) {
            controller.desk = controller.desk.copyWithNewField(controller.desk.field.checkWalk(controller.desk.field.getCellAtPosition(row, col).getNinja(), d))

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
            controller.desk = controller.desk.copyWithNewField(controller.desk.field.checkWalk(controller.desk.field.getCellAtPosition(row, col).getNinja(), d))
            controller.switchState(State.WALKED)
        } else {
            controller.switchState(State.DIRECTION_DOES_NOT_EXIST)
        }
    }


}
