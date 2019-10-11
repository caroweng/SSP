package de.htwg.se.ninja.model

case class Ninja(weapon: Weapon.weapon, player: Player)  {
  def changeWeapon(w: Weapon.weapon) : Ninja = this.copy(weapon = w)
}
