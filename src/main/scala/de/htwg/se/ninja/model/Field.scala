package de.htwg.se.ninja.model

import Weapons.Weapons

case class Field( row: Int, col: Int) {

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

  def randWeapon(): Weapons = {
    val r = scala.util.Random
    val n: Int = r.nextInt(3)
    if(n == 0) {
      Weapons.scissors
    } else if(n == 1){
      Weapons.stone
    } else {
      Weapons.paper
    }
  }

}

