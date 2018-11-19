package de.htwg.se.ninja.model
import Direction.Direction

class Play (field: Field){


  def walk(d: Direction, row: Int, col: Int): Unit ={
    if(d == Direction.right) {
      if(col + 1 != null) {
        if(field.matrix(row)(col+1) == Cell(None)) {
          field.matrix(row)(col+1) = field.matrix(row)(col)
          field.matrix(row)(col) = Cell(None)
        } else {

        }
      }
      if(...){
        fight()
      }
    } else if(d == Direction.left) {

      if(...){
        fight()
      }
    } else if(d == Direction.up) {

      if(...){
        fight()
      }
    } else if(d == Direction.down) {

      if(...){
        fight()
      }
    } else {
      throw new IllegalArgumentException("Ungültige Eingabe")
    }
  }

  def fight(n1: Ninja, n2: Ninja): Unit = {
    if(n1.weapon == n2.weapon) {

    } else if() {

    }
  }

  def delete(n: Ninja): Unit = {

  }

}
