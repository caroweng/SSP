package de.htwg.se.ninja.model

import scala.util.Random

object Weapon extends Enumeration {
  type weapon = Value
  val scissors, stone, paper, flag = Value

  def randWeapon(): Weapon.weapon = {
//    val r = new Random()
//    val n = r.nextInt(3)
    val t = System.currentTimeMillis()
    val r =  t % 3
    r match {
      case 0 => Weapon.scissors
      case 1 => Weapon.stone
      case 2 => Weapon.paper
    }
  }
}
