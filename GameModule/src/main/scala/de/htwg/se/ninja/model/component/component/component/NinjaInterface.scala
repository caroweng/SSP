package de.htwg.se.ninja.model.component.component.component

import de.htwg.se.ninja.model.component.component.component.component.Weapon

trait NinjaInterface {
    val weapon: Weapon.weapon
    val playerId: Int
    val ninjaId: Int

    def changeWeapon(w: Weapon.weapon) : NinjaInterface
    def copyWithNewWeapon(w: Weapon.weapon): NinjaInterface

}
