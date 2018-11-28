package de.htwg.se.ninja.model
import Direction.Direction
import Weapons.Weapons

case class Play (field:Field, row: Int, col: Int, d :Direction) {

  field(row)(col) match {
    case None =>
    case Some(n1: Ninja) => walk(n1)
  }

  val n1: Ninja = field.matrix(row)(col)[Ninja]

  def walk(n: Ninja): Field = {

    d match {
      case Direction.right =>
        if (exists(row, col + 1))
          move(row, col + 1)
      case Direction.left =>
        if (exists(row, col - 1))
          move(row, col - 1)
      case Direction.up =>
        if (exists(row + 1, col))
          move(row + 1, col)
      case Direction.down =>
        if (exists(row - 1, col))
          move(row - 1, col)
    }
    field
  }
    def move(nrow: Int, ncol: Int): Unit = {
      field(nrow)(ncol) match {
        case None => field(nrow)(ncol) = field(row)(col)
          field(row)(col) = Cell(None)

        case Some(n2: Ninja) =>
          fight(n1, n2) match {
            case None => field.matrix(nrow)(ncol) = Cell(None)
            case Some(n) => field.matrix(nrow)(ncol) = field.matrix(row)(col)
              field.matrix(row)(col) = Cell(None)
          }
      }

    }

  def fight(n1: Ninja, n2: Ninja): Option[Ninja] = {
    if(n1.weapon == n2.weapon) {
      rematch(n1, n1)
    }
    if(weaponWeight(n1.weapon, n2.weapon)) {
      Some(n1)
    } else {
      None
    }
  }

  def rematch(n1: Ninja, n2: Ninja): Unit = {
    n1.weapon = field.randWeapon()
    n2.weapon = field.randWeapon()
    fight(n1, n2)
  }

  def weaponWeight(w1: Weapons, w2: Weapons) : Boolean = {
      w1 match {
        case Weapons.stone =>
          if (w2 == Weapons.paper)
            false
          else
            true
        case Weapons.scissors =>
          if (w2 == Weapons.stone)
            false
          else
            true
        case Weapons.paper =>
          if (w2 == Weapons.scissors)
            false
          else
            true
      }
  }

  def exists(r: Int, c: Int): Boolean = {
    field.matrix(r)(c) match {
      case Cell(None) || Cell(Some(n))  => true
      case _ => false
    }
  }
}
