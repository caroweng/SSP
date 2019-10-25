package de.htwg.se.ninja.model

case class Ninja(weapon: Weapon.weapon, playerId: Int, ninjaId: Int)  {
  def changeWeapon(w: Weapon.weapon) : Ninja = this.copy(weapon = w)
}
