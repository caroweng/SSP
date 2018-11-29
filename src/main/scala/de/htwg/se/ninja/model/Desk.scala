package de.htwg.se.ninja.model
import Direction.value
import Weapon.value

case class Desk(field: Field, player1 : Player, player2: Player) {



  def walk(player: Player, ninja: Ninja, direction: Direction.value) : Desk = ???





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
    copy(field)
  }
    def move(nrow: Int, ncol: Int): Field = {
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
    val fc = new FieldCreator(row, col)
    n1.weapon = fc.randWeapon()
    n2.weapon = fc.randWeapon()
    fight(n1, n2)
  }

  def weaponWeight(w1: value, w2: value) : Boolean = {
      w1 match {
        case Weapon.stone =>
          if (w2 == Weapon.paper)
            false
          else
            true
        case Weapon.scissors =>
          if (w2 == Weapon.stone)
            false
          else
            true
        case Weapon.paper =>
          if (w2 == Weapon.scissors)
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
