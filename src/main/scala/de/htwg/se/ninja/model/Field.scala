package de.htwg.se.ninja.model

import Weapons.Weapons

case class Field(matrix: Array[Array[Cell]], row: Int, col: Int) {
  def this() = this (Array.ofDim[Cell](row, col))

  def newGame(): Field = {
    var matrix = Array.ofDim[Cell](row,col)
    if (row <= 1 && col <= 2  ) {
      throw new IllegalArgumentException("Ungültige Arraygröße")
    }
    if(row%3 )

    //Team1(true)
    /*for(i <- 0 to 1) {
      for(j <- 0 to 5) {
        matrix(i)(j) = Cell(Some(Ninja(true, randWaffe())))
      }
    }
    for(i <- 2 to 3) {
      for(j <- 0 to 5) {
        matrix(i)(j) = Cell(None)
      }
    }

    //Team2(false)
    for(k <- 4 to 5) {
      for(l <- 0 to 5) {
        matrix(k)(l) = Cell(Some(Ninja(false, randWaffe())))
      }
    }*/
    Field(matrix)
  }


  def randWaffe(): Weapons = {
    val r = scala.util.Random
    r.nextInt(3)
    if(r == 0) {
      return Weapons.Stein
    } else if(r == 1){
      return Weapons.Schere
    } else {
      return Weapons.Papier
    }
  }
}

