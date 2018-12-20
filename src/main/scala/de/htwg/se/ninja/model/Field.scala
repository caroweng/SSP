package de.htwg.se.ninja.model

case class Field(matrix: Array[Array[Cell]]) {

  def matrix(tupel: (Int, Int)): Cell = matrix(tupel._1)(tupel._2)

  def getPosition(n1: Ninja): (Int, Int) = {
    for(r <- matrix.indices)
        for(c <- matrix.indices)
          if (matrix(r)(c).optNinja.getOrElse("kein Ninja") == Ninja(n1.weapon, n1.player, n1.id))
            return (r, c)

    throw new NoSuchElementException()
  }

  def isNinjaOfPlayerAtPosition(player: Player, row: Int, col: Int): Boolean = {
    if (matrix(row)(col).exists() && matrix(row, col).getNinja().player == player) {
      return true
    }
    false
  }


  def move(n1: Ninja, direction: Direction.direction): Field = {
    if(n1.weapon == Weapon.flag) throw new IllegalStateException()

    val pos = getPosition(n1)
    val newpos = add(pos, Direction.getDirectionIndex(direction))
    matrix(newpos).optNinja match {
      case None =>  this.-(n1, getPosition(n1)).+(n1, newpos)

      case Some(n2) => if(n2.player == n1.player) throw new IllegalArgumentException
                          this.-(n1, getPosition(n1)).+(fight(n1, n2), newpos)

    }
  }

  def -(ninja: Ninja, pos: (Int,Int)): Field = copy(matrix.updated(pos._1, matrix(pos._1).updated(pos._2, Cell(None))))

  def +(ninja: Ninja, pos: (Int,Int)): Field = copy(matrix.updated(pos._1, matrix(pos._1).updated(pos._2, Cell(Some(ninja)))))

  def fight(n1: Ninja, n2: Ninja): Ninja = {
    if(n1.weapon == n2.weapon) {
      val newN = n1.copy(weapon = Weapon.randWeapon())
      if(weaponWeight(newN.weapon, n2.weapon)) return newN else return n2
    }
    if(weaponWeight(n1.weapon, n2.weapon)) n1 else n2
  }

  def weaponWeight(w1: Weapon.weapon, w2: Weapon.weapon) : Boolean = {
    w1 match {
      case Weapon.stone =>
        if (w2 == Weapon.paper) false else true
      case Weapon.scissors =>
        if (w2 == Weapon.stone) false else true
      case Weapon.paper =>
        if (w2 == Weapon.scissors) false else true
    }
  }

  def exists(ninja: Ninja, direction: Direction.direction): Boolean = inBounds(add(getPosition(ninja), Direction.getDirectionIndex(direction)))

  def add(base: (Int, Int), amount: (Int, Int)): (Int, Int) = (base._1 + amount._1, base._2 + amount._2)

  def inBounds(tuple: (Int, Int)): Boolean = tuple._1 < matrix.length && tuple._1 >= 0 && tuple._2 < matrix.length && tuple._2 >= 0
}