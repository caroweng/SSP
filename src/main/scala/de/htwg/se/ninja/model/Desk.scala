package de.htwg.se.ninja.model

import Direction.direction

case class Desk(field: Field, player1 : Player, player2: Player) {

  def setNewGame(): Desk = this.copy(field = setNinjas(setEmpty(field)))

  def setEmpty(field: Field): Field = {
    val field2: Field = Field(field.matrix)
    for {i <- field.matrix.indices
         j <- field.matrix.indices}
      field2.matrix(i)(j) = Cell(None)
    field.copy(matrix = field2.matrix)
  }

  def setNinjas(field: Field): Field = {
    var n, m = 0
    val field2: Field = Field(field.matrix)
    for {r <- 0 until this.ninjaRows(field)
         c <- field.matrix.indices} {
      field2.matrix(r)(c) = Cell(Some(Ninja(Weapon.randWeapon(), player1, n)))
      n += 1
    }
    for {r <- field.matrix.length - this.ninjaRows(field) until field.matrix.length
         c <- field.matrix.indices} {
      field2.matrix(r)(c) = Cell(Some(Ninja(Weapon.randWeapon(), player2, m)))
      m += 1
    }
    field.copy(matrix = field2.matrix)
  }

  def ninjaRows(field: Field): Int = if (field.matrix.length / 3 < 2) 1 else 2

  def walk(player: Player, ninja: Ninja, direction: direction): Desk = this.copy(field = field.move(ninja, direction))
  
  def toString(player: Player): String = {
    //print(player1 + " und " + player2)
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
    val cell: Cell = field.matrix(row, col)
    if(cell.optNinja.isEmpty) {
      str = "[  ]"
      str
    } else {
      val ninja: Ninja = cell.getNinja()
      if(ninja.player.name == curPlayer.name) {
        if (ninja.player.name == this.player1.name) str = " 1" else str = " 2"
          ninja.weapon match {
            case Weapon.flag => str.concat("f ")
            case Weapon.stone => str.concat("r ")
            case Weapon.paper => str.concat("p ")
            case Weapon.scissors => str.concat("s ")
          }
      } else {
        str = " xx "
        str
      }
    }
  }

  def setFlag(player: Player, row : Int , col : Int): Desk = {
    val d2: Field = this.field
    val ninja: Ninja = this.field.matrix(row)(col).getNinja()

    d2.matrix(row)(col) = Cell(Some(Ninja(Weapon.flag, ninja.player, ninja.id)))
    copy(field = d2)
  }

  def win(row: Int, col: Int, d: direction): Boolean = {
    val n1: Ninja = this.field.matrix(this.field.add((row,col), Direction.getDirectionIndex(d))).optNinja.getOrElse(return  false)
    if(n1.weapon == Weapon.flag && n1.player != field.matrix(row, col).getNinja().player) true else false
  }

  def changeTurns(): Desk = {
    val s1: Turn.turn = player1.state
    val p1: Player = player1.changeTurn(player2.state)
    val p2: Player = player2.changeTurn(s1)
    this.copy(player1 = p1, player2 = p2)
  }
}
