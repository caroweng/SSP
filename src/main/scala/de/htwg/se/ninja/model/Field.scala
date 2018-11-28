package de.htwg.se.ninja.model

case class Field(matrix : Array[Array[Cell]]) {
  def this(row : Int, col : Int) = {
    this(Array.ofDim[Cell](row,col))
      for {r <- 0 until row
           c <- 0 until col}
       matrix(r)(c) = Cell(None)
  }

}