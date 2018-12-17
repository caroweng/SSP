package de.htwg.se.ninja.model

case class Desk(field: Field, player1 : Player, player2: Player) {

  def setNewGame(): Desk = this.copy(field = setNinjas(setEmpty(field)), player1 = this.player1.copy(flag = false), player2 = this.player2.copy(flag = false))

  def setEmpty(field: Field): Field = {
    val field2 = Field(field.matrix)
    for {i <- field.matrix.indices
         j <- field.matrix.indices}
      field2.matrix(i)(j) = Cell(None)
    field.copy(matrix = field2.matrix)
  }

  def setNinjas(field: Field): Field = {
    var n, m = 0
    val field2 = Field(field.matrix)
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

  def walk(player: Player, ninja: Ninja, direction: Direction.direction): Desk = {
    field.exists(ninja, direction) match {
      case true => this.copy(field = field.move(ninja, direction))
      case false => throw new NoSuchElementException
    }
  }

  def toDirection(dir: String): Direction.direction = {
    dir match{
      case "down" => Direction.down
      case "up" => Direction.up
      case "left" => Direction.left
      case "right" => Direction.right
    }
  }

  def toPlayer(name: String): Player = {
    name match{
      case this.player1.name => this.player1
      case this.player2.name => this.player2
    }
  }

  override def toString: String = {
    val rows = this.field.matrix.length
    val line =("|" + "  " )*rows + "|\n"
    var box = "\n" + (line * rows)

    for {i <- this.field.matrix.indices
         j <- this.field.matrix.indices}
        box = box.replaceFirst("  ",  this.field.matrix(i,j).toString)
    box
  }

  def getPlayerWithName(name: String): Player = if(this.player1.name  == name) player1 else player2

  def setFlag(player: Player, row : Int , col : Int): Desk = {
    val d2 = this.field
    val ninja = this.field.matrix(row)(col).ninja.get
    if(player.flag)
      throw new IllegalArgumentException
    if(ninja.player != player)
      throw new IllegalStateException
    d2.matrix(row)(col) = Cell(Some(Ninja(Weapon.flag, ninja.player, ninja.id)))
    if(player == player1)
       copy(field = d2, player1 = this.player1.copy(flag = true))
    else
       copy(field = d2, player2 = this.player2.copy(flag = true))
  }

  def win(row: Int, col: Int, d: Direction.direction): Boolean = {
    val n1 = this.field.matrix(this.field.add((row,col), Direction.getDirectionIndex(d))).ninja.getOrElse(return  false)
    if(n1.weapon == Weapon.flag) true else false
  }

  def changeTurns(): Desk = {
    copy(player1 = this.player1.changeTurn(player2.state), player2 = this.player2.changeTurn(player1.state))
  }
}
