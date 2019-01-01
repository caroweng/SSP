package de.htwg.se.ninja.util

trait Command {

  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit

}

