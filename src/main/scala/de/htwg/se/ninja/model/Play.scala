package de.htwg.se.ninja.model
import Direction.Direction
import Weapons.Weapons
import de.htwg.se.ninja.model


class Play (field: Field){


  def walk(d: Direction, row: Int, col: Int): Unit = {

    d match {
      case Direction.right =>
        if (field.matrix(row + 1)(col) != null)
          move(row, col + 1)
      case Direction.left =>
        if (field.matrix(row - 1)(col) != null)
          move(row, col - 1)
      case Direction.up =>
        if (field.matrix(row)(col + 1) != null)
          move(row + 1, col)
      case Direction.down =>
        if (field.matrix(row)(col - 1) != null)
          move(row - 1, col)
    }


    def move(nrow: Int, ncol: Int): Unit = {
      if (field.matrix(nrow)(ncol) != Cell(None)) {
        if(fight(field.matrix(row)(col)(Some(Ninja).get), field.matrix(nrow)(ncol)(Some(Ninja).get))!= None) {
          val n: Ninja = field.matrix(nrow)(ncol)[Ninja]
          field.matrix(nrow)(ncol) = field.matrix(row)(col)
          field.matrix(row)(col) = Cell(None)
        } else {
          field.matrix(nrow)(ncol) = Cell(None)
        }
      } else {
        field.matrix(nrow)(ncol) = field.matrix(row)(col)
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
}
