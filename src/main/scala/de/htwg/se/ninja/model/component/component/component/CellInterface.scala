package de.htwg.se.ninja.model.component.component.component


trait CellInterface {
    val optNinja: Option[NinjaInterface]

    def exists(): Boolean
    def getNinja(): NinjaInterface
    def removeNinja(): CellInterface

}
