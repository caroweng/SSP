import de.htwg.se.ninja.model.{Cell, Ninja, Team, Weapons}

case class Field(matrix : Array[Array[Cell]]) {
  def this(row : Int, col : Int) = {
    this(Array.ofDim[Cell](row,col))
    for {r <- 0 until row
         c <- 0 until col}
      matrix(r)(c) = Cell(None)
  }
}

val field = new Field(2,3)
//field.matrix(1)(1) = Cell(Some (Ninja (Team.T2, Weapons.paper )))
field.matrix(1)(1)
