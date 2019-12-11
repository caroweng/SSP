package de.htwg.se.ninja.model

object Weapon extends Enumeration {
    type weapon = Value
    val scissors: Weapon.Value = Value("s")
    val rock: Weapon.Value = Value("r")
    val paper: Weapon.Value = Value("p")
    val flag: Weapon.Value = Value("f")

    def createWeapon(number: Int): Weapon.weapon = {
        number match {
            case 0 => Weapon.scissors
            case 1 => Weapon.rock
            case 2 => Weapon.paper
        }
    }

    override def toString(): String = {
        "weapon"
    }
}
