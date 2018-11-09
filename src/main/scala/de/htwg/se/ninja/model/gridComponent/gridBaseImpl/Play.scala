package de.htwg.se.ninja.model.gridComponent.gridBaseImpl

import de.htwg.se.ninja.model.gridComponent.gridBaseImpl.Waffen.Waffen

class Play (Feld: Spielfeld){
  def newGame(): Unit = {
    //val Feld = Spielfeld()
    //Team1(true)
    for(i <- 0 to 1) {
      for(j <- 0 to 5) {
        Feld.matrix(i)(j) = Ninja(true, randWaffe())
      }
    }
    //Team2(false)
    /*for(k <- 4 to 5) {
      for(l <- 0 to 5) {
        Feld.matrix((k)(l)) = Ninja(false, randWaffe())
      }
    }*/
  }

  def randWaffe(): Waffen = {
    val r = scala.util.Random
    r.nextInt(3)
    if(r == 0) {
      return Waffen.Stein
    } else if(r == 1){
      return Waffen.Schere
    } else {
      return Waffen.Papier
    }
  }

  def laufen(): Unit ={

  }

  def kämpfen(): Unit = {

  }

}
