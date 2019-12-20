package de.htwg.se.ninja.model.component.component.component.component

import de.htwg.se.ninja.model.component.component.component.{CellInterface, NinjaInterface}

case class Cell(optNinja: Option[NinjaInterface]) extends CellInterface{
  def exists(): Boolean = optNinja.isDefined
  def getNinja(): NinjaInterface = optNinja.get
  def removeNinja(): CellInterface = Cell(None)
}
