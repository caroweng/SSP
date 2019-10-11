package de.htwg.se.ninja.model

case class Cell(optNinja: Option[Ninja]) {
  def exists(): Boolean = optNinja.isDefined
  def getNinja(): Ninja = optNinja.get
}