package de.htwg.se.ninja.model

import scala.util.Random

object Weapon extends Enumeration {
  type weapon = Value
  val scissors, stone, paper, flag = Value

  def randWeapon(): Weapon.weapon = {
    val r: Random = new Random()
    val n: Int = r.nextInt(3)
//    val t = System.currentTimeMillis()
//    val r =  t % 3
    n match {
      case 0 => Weapon.scissors
      case 1 => Weapon.stone
      case 2 => Weapon.paper
    }
  }

  override def toString: String = {
    this match {
      case Weapon.scissors => "s"
      case Weapon.paper => "p"
      case Weapon.stone => "r"
    }
  }
}
