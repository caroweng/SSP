package de.htwg.se.ninja.model.component

import com.google.inject.Inject
import de.htwg.se.ninja.model._
import de.htwg.se.ninja.model.component.component.component.component.Direction.direction
import de.htwg.se.ninja.model.component.component.component.component.{Direction, StateOfPlayer, Weapon}
import de.htwg.se.ninja.model.component.component.component.{FieldInterface, NinjaInterface, PlayerInterface}

case class Desk @Inject()(field: FieldInterface, player1: PlayerInterface, player2: PlayerInterface) extends DeskInterface {

  def setNewGame(): Desk = this.copy(field = field.setInitial())

  def win(row: Int, col: Int, d: direction): Boolean = {
    val n1: NinjaInterface = this.field.getCellAtPosition(this.field.addDirection((row,col), Direction.getDirectionIndex(d))).optNinja.getOrElse(return  false)
    if(n1.weapon == Weapon.flag && n1.playerId != field.getCellAtPosition(row, col).getNinja().playerId) true else false
  }

  def changeTurns(): Desk = {
    val s1: StateOfPlayer.stateOfPlayer = player1.state
    val p1: PlayerInterface = player1.changeState(player2.state)
    val p2: PlayerInterface = player2.changeState(s1)
    this.copy(player1 = p1, player2 = p2)
  }

  def copyWithNewPlayer(playerId: Int, newPlayer: PlayerInterface): Desk =  {
    if(playerId == 1)
      copy(player1 = newPlayer)
    else
      copy(player2 = newPlayer)
  }

  def copyWithNewField(newField: FieldInterface): Desk = {
    copy(field = newField)
  }

  def copyDesk(): Desk = {
    copy()
  }
}
