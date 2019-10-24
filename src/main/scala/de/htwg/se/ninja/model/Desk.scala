package de.htwg.se.ninja.model

import Direction.direction

case class Desk(field: Field, player1 : Player, player2: Player) {

  def setNewGame(): Desk = this.copy(field = field.setNewField())

  def toString(player: Player): String = {
    val rows: Int= this.field.matrix.length
    val lineseparator: String = "  +" + ("----+") * rows + "\n"
    val line: String = (" |" + "   " )*rows + " |\n"
    var box: String = "\nrow 0  | 1  | 2  | 3  | 4  | 5" + "\n" + ( lineseparator + "n" +line ) * rows + lineseparator

    for (i <- this.field.matrix.indices) {
      box = box.replaceFirst("n", i.toString)
      for (j <- this.field.matrix.indices)
        box = box.replaceFirst("    ", this.toString(player, i, j))
    }
    box
  }

  def toString(curPlayer: Player, row: Int, col: Int): String ={
    var str: String = ""
    val cell: Cell = this.field.getCellAtPosition(row, col)
    if(cell.optNinja.isEmpty) {
      str = "[  ]"
      str
    } else {
      val ninja: Ninja = cell.getNinja()
      if(ninja.playerId == curPlayer.id) {
        if (ninja.playerId == this.player1.id) str = " 1" else str = " 2"
          ninja.weapon match {
            case Weapon.flag => str.concat("f ")
            case Weapon.`rock` => str.concat("r ")
            case Weapon.paper => str.concat("p ")
            case Weapon.scissors => str.concat("s ")
          }
      } else {
        str = " xx "
        str
      }
    }
  }

  def win(row: Int, col: Int, d: direction): Boolean = {
    val n1: Ninja = this.field.getCellAtPosition(this.field.add((row,col), Direction.getDirectionIndex(d))).optNinja.getOrElse(return  false)
    if(n1.weapon == Weapon.flag && n1.playerId != field.getCellAtPosition(row, col).getNinja().playerId) true else false
  }

  def changeTurns(): Desk = {
    val s1: StateOfPlayer.stateOfPlayer = player1.state
    val p1: Player = player1.changeState(player2.state)
    val p2: Player = player2.changeState(s1)
    this.copy(player1 = p1, player2 = p2)
  }
}
