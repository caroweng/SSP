package de.htwg.se.ninja.model

case class Desk(field: Field, player1 : Player, player2: Player) {
  def setNewGame(): Desk = this.copy(field = setNinjas(setEmpty(field)))

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
}
