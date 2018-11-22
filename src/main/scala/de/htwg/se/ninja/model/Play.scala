package de.htwg.se.ninja.model
import Direction.Direction

class Play (field: Field){


  def walk(d: Direction, row: Int, col: Int): Unit ={

    d : Direction match{
      case Direction.right =>
        if (field.matrix(row+1)(col) != null)
          move(row, col+1)
      case Direction.left =>
        if (field.matrix(row-1)(col) != null)
          move(row, col-1)
      case Direction.up =>
        if (field.matrix(row)(col+1) != null)
        move(row+1, col )
      case Direction.down =>
        if (field.matrix(row)(col -1) != null)
          move(row-1, col)
    }

    def move(nrow : Int,ncol : Int): Unit ={
      if(field.matrix(nrow)(ncol) != Cell(None)) {
        fight(field.matrix(nrow)(ncol)(Some(Ninja)),field.matrix(nrow)(ncol)(Some(Ninja)))
      }else {
        field.matrix(nrow)(ncol) = field.matrix(row)(col)
      }
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
