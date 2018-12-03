package de.htwg.se.ninja.model

case class Cell(ninja: Option[Ninja]) {
  def -(ninja: Ninja): Cell = Cell(None)
  def +(ninja: Ninja): Cell = Cell(Some(Ninja(ninja.weapon, ninja.player, ninja.id)))
}

