package de.htwg.se.ninja.model.component.component.component

import de.htwg.se.ninja.model.component.component.component.component.Cell

trait CellInterface {
    val optNinja: Option[NinjaInterface]

    def exists(): Boolean
    def getNinja(): NinjaInterface
    def removeNinja(): Cell

}
