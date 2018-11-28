package de.htwg.se.ninja.model

import de.htwg.se.ninja.model.Weapons.Weapons

class FieldCreator ( row : Int , col : Int) {


   def newGame(): Field = {

     val field = new Field(row,col)

     if (row <= 2 && col <= 1) {
      throw new IllegalArgumentException ("Ungültige Arraygröße")
     }

   for (r <- 0 to row - 1) {
     field(r)().foreach(col => Cell (None) )
   }




     if (row / 3 < 2) {
     for (j <- 0 to col - 1) {
     field (0) (j) = Cell (Some (Ninja (Team.T1, randWeapon () ) ) )
     field (row - 1) (j) = Cell (Some (Ninja (Team.T2, randWeapon () ) ) )
   }
   } else {
     for (i <- 0 to 1) {
     field (i).foreach (Cell => Some (Ninja (Team.T1, randWeapon () ) ) )
   }
     for (k <- row - 2 to row - 1) {
     field (k).foreach (Cell => Some (Ninja (Team.T2, randWeapon () ) ) )
   }
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
