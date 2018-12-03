package de.htwg.se.ninja.model

case class Field(matrix: Array[Array[Cell]]) {
//new Field(Array.ofDim[Cell](3,3))

  def matrix(tupel: (Int, Int)): Cell = matrix(tupel._1)(tupel._2)

  def getPosition(ninja: Ninja): (Int, Int) = {
    for{r <- 0 until matrix.length
        c <- 0 until matrix.length}
      if(matrix(r)(c) == Cell(Some(Ninja(ninja.weapon, ninja.player, ninja.id))))
        return (r,c)
    throw new NoSuchElementException()
  }

  def move(ninja: Ninja, direction: Direction.direction): Field = {
    val a = add(getPosition(ninja), Direction.getDirectionIndex(direction))
    matrix(a).ninja match {
      case None => matrix(getPosition(ninja)).-(ninja)
                   matrix(a).+(ninja)
                   this
      case Some(n2) => fight(ninja,  n2)
                       this
    }
  }

  def fight(n1: Ninja, n2: Ninja): Option[Ninja] = {
    if(n1.weapon == n2.weapon) rematch(n1, n2)
    if(weaponWeight(n1.weapon, n2.weapon)) Some(n1) else None
  }

  def rematch(n1: Ninja, n2: Ninja): Unit = {
    n1.copy(weapon = Weapon.randWeapon())
    n2.copy(weapon = Weapon.randWeapon())
    this.fight(n1, n2)
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