package de.htwg.se.ninja.model

import Weapons.Weapons

case class Field( row: Int, col: Int) {
  //def this(size: Int) = this(Array.ofDim[Cell](row,col))

  var matrix= Array.ofDim[Cell](row,col)

    if (row <= 2 && col <= 1) {
      throw new IllegalArgumentException("Ungültige Arraygröße")
    }

    for (r <- 0 to row - 1) {
      matrix(r).foreach(col => Cell(None))
    }

    if (row / 3 < 2) {
      for (j <- 0 to col - 1) {
        matrix(0)(j) = Cell(Some(Ninja(Team.T1, randWeapon())))
        matrix(row - 1)(j) = Cell(Some(Ninja(Team.T2, randWeapon())))
      }
    } else {
      for (i <- 0 to 1) {
       matrix(i).foreach(Cell => Some(Ninja(Team.T1, randWeapon())))
      }
      for (k <- row - 2 to row - 1) {
       matrix(k).foreach(Cell => Some(Ninja(Team.T2, randWeapon())))
      }
    }



  def randWeapon(): Weapons = {
    val r = scala.util.Random
    val n: Int = r.nextInt(3)
    n match{
      case 0 => Weapons.scissors
      case 1 => Weapons.stone
      case 2 => Weapons.paper
    }
  }
}

