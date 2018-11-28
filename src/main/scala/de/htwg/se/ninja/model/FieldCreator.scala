package de.htwg.se.ninja.model

import de.htwg.se.ninja.model.Weapons.Weapons

object FieldCreator  {

   def newGame(row:Int, col:Int): Field = {
     val field = new Field(row,col)
      if (row <= 2 && col <= 1) {
        throw new IllegalArgumentException ("Ungültige Arraygröße")
      }

       if (row / 3 < 2) {
         for (j <- 0 until col) {
         field.matrix(0)(j) = Cell (Some (Ninja (Team.T1, randWeapon () ) ) )
         field.matrix(row - 1)(j) = Cell (Some (Ninja (Team.T2, randWeapon () ) ) )
          }
         field
       } else {
         for {r <- 0 to 1
              c <- 0 until col}
           field.matrix(r)(c) = Cell(Some (Ninja (Team.T1, randWeapon())))

         for {r <- row-2 until row
              c <- 0 until col}
           field.matrix(r)(c) = Cell( Some (Ninja (Team.T2, randWeapon())))
         field
       }
   }

  def randWeapon(): Weapons = {
    val r = scala.util.Random
    val n: Int = r.nextInt(3)
    n match {
      case 0 => Weapons.scissors
      case 1 => Weapons.stone
      case 2 => Weapons.paper
    }
  }
}
