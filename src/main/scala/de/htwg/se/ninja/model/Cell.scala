package de.htwg.se.ninja.model

case class Cell(ninja: Option[Ninja]) {

  def +(ninja: Ninja): Cell = ???
  def -(ninja: Ninja): Cell = ???
}

