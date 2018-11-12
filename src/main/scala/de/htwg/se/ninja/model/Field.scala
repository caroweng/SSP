package de.htwg.se.ninja.model

import Weapons.Weapons

case class Field(matrix: Array[Array[Cell]], row: Int, col: Int) {
  def this(r: Int, c: Int) = this (Array.ofDim[Cell](r, c))

  newGame()

  def newGame(): Field = {
    var matrix = Array.ofDim[Cell](row,col)
    if (row <= 2 && col <= 1  ) {
      throw new IllegalArgumentException("Ungültige Arraygröße")
    }
    for(r <- 0 to row-1) {
      for(c <- 0 to col-1) {
        matrix(r)(c) = Cell(None)
      }
    }
    if(row/3 < 2) {
      for(j <- 0 to col-1) {
        matrix(0)(j) = Cell(Some(Ninja(Team.T1, randWeapon())))
        matrix(row-1)(j) = Cell(Some(Ninja(Team.T2, randWeapon())))
      }
    } else {
      for(i <- 0 to 1) {
        for (j <- 0 to col - 1) {
          matrix(i)(j) = Cell(Some(Ninja(Team.T1, randWeapon())))
        }
      }
      for(k <- row-2 to row-1) {
        for (l <- 0 to col-1) {
          matrix(k)(l) = Cell(Some(Ninja(Team.T2, randWeapon())))
        }
      }
    }

    Field(matrix, row, col)
  }


  def randWeapon(): Weapons = {
    val r = scala.util.Random
    r.nextInt(3)
    if(r == 0) {
      return Weapons.scissors
    } else if(r == 1){
      return Weapons.stone
    } else {
      return Weapons.paper
    }
  }
}

