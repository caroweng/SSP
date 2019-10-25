package de.htwg.se.ninja.model

import Direction.direction

case class Desk(field: Field, player1 : Player, player2: Player) {

  def setNewGame(): Desk = this.copy(field = field.setInitial())

  def win(row: Int, col: Int, d: direction): Boolean = {
    val n1: Ninja = this.field.getCellAtPosition(this.field.addDirection((row,col), Direction.getDirectionIndex(d))).optNinja.getOrElse(return  false)
    if(n1.weapon == Weapon.flag && n1.playerId != field.getCellAtPosition(row, col).getNinja().playerId) true else false
  }

  def changeTurns(): Desk = {
    val s1: StateOfPlayer.stateOfPlayer = player1.state
    val p1: Player = player1.changeState(player2.state)
    val p2: Player = player2.changeState(s1)
    this.copy(player1 = p1, player2 = p2)
  }
}
