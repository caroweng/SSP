package de.htwg.se.ninja.controller

import de.htwg.se.ninja.model.Player
import de.htwg.se.ninja.util.Command

class setFlagCommand(row: Int, col: Int, player: Player,controller: Controller) extends Command{
  override def doStep: Unit =   controller.desk = controller.desk.setFlag(player, row, col)

  override def undoStep: Unit = controller.desk = controller.desk.setFlag(player, row, col)//?????????????

  override def redoStep: Unit = controller.desk = controller.desk.setFlag(player, row, col)

}
