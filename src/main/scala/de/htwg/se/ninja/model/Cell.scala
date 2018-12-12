package de.htwg.se.ninja.model

case class Cell(ninja: Option[Ninja]) {


  override def toString: String ={
    if(this.ninja.isEmpty) {
      val str = ""
      str
    } else {
      val str =  ninja.get.player.name + " " + ninja.get.weapon.toString()
      str
    }
  }
}