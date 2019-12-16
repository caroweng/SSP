package de.htwg.se.ninja.model.component.component.component.component

import de.htwg.se.ninja.model.component.component.component.NinjaInterface

case class Ninja(weapon: Weapon.weapon, playerId: Int, ninjaId: Int) extends NinjaInterface{
  def changeWeapon(w: Weapon.weapon): NinjaInterface = this.copy(weapon = w)
  def copyWithNewWeapon(w: Weapon.weapon): NinjaInterface = copy(weapon = w)
}
