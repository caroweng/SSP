package de.htwg.se.ninja.controller

import de.htwg.se.ninja.model.Direction.Direction
import de.htwg.se.ninja.model.{Field, Play}
import de.htwg.se.ninja.util.Observable

class Controller (var field: Field) extends Observable{

  def createNewField(row: Int, col: Int):Unit = {
    field = new Field(row, col)
    notifyObservers
  }

  def fieldToString: String = field.toString

  def walk(d: Direction, row: Int, col: Int): Unit = {
    field = Play(field).walk(d, row, col)
    notifyObservers
  }

}