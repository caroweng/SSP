package de.htwg.se.ninja.model

object Weapon extends Enumeration {
  type weapon = Value
  val scissors, stone, paper, flag = Value

  def randWeapon(): Weapon.weapon = {
    val r = scala.util.Random
    val n: Int = r.nextInt(3)
    n match {
      case 0 => Weapon.scissors
      case 1 => Weapon.stone
      case 2 => Weapon.paper
    }
  }
}
