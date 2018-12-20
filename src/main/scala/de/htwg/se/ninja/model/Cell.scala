package de.htwg.se.ninja.model

case class Cell(optNinja: Option[Ninja]) {
  override def toString: String ={
    if(this.optNinja.isEmpty) {
      val str = ""
      str
    } else {
      val str =  optNinja.get.player.name + " " + optNinja.get.weapon.toString()
      str
    }
  }

  def exists(): Boolean = optNinja.isDefined
  def getNinja(): Ninja = optNinja.get
}