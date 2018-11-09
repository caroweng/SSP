package de.htwg.se.ninja.model.gridComponent.gridBaseImpl

case class Spielfeld() {
  var matrix = Array.ofDim[Ninja](6,6)
  matrix: Array[Array[Ninja]]

  matrix(0)(0) = Ninja(true, Waffen.Stein)


}

/*object Spielfeld {
  def apply(): Spielfeld = {
  var m = new Spielfeld
  m.matrix = matrix
  m
  }
}*/


